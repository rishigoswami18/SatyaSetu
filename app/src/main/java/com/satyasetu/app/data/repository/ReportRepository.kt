package com.satyasetu.app.data.repository

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.satyasetu.app.data.model.Report
import com.satyasetu.app.util.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for safe report submission and management
 * All reports go to pending_reports — never auto-published
 */
@Singleton
class ReportRepository @Inject constructor(
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
) {
    private val pendingRef = database.reference.child(Constants.DB_REPORTS)
    private val approvedRef = database.reference.child(Constants.DB_APPROVED_REPORTS)

    /**
     * Submit a new report (goes to pending_reports)
     */
    suspend fun submitReport(report: Report): Result<String> {
        return try {
            val reportId = pendingRef.push().key
                ?: throw Exception("Failed to generate report ID")

            val reportWithId = report.copy(
                reportId = reportId,
                status = Report.STATUS_PENDING,
                createdAt = System.currentTimeMillis()
            )

            pendingRef.child(reportId).setValue(reportWithId.toMap()).await()
            Result.success(reportId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Upload report image to Firebase Storage
     */
    suspend fun uploadReportImage(uri: Uri): Result<String> {
        return try {
            val fileName = "report_${UUID.randomUUID()}.jpg"
            val ref = storage.reference
                .child(Constants.STORAGE_REPORT_IMAGES)
                .child(fileName)

            ref.putFile(uri).await()
            val downloadUrl = ref.downloadUrl.await()
            Result.success(downloadUrl.toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get user's own reports
     */
    fun observeUserReports(userId: String): Flow<List<Report>> = callbackFlow {
        val query = pendingRef.orderByChild("userId").equalTo(userId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reports = snapshot.children.mapNotNull { child ->
                    child.getValue(Report::class.java)
                }.sortedByDescending { it.createdAt }
                trySend(reports)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    /**
     * Get approved reports (public view)
     */
    fun observeApprovedReports(): Flow<List<Report>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reports = snapshot.children.mapNotNull { child ->
                    child.getValue(Report::class.java)
                }.sortedByDescending { it.createdAt }
                trySend(reports)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        approvedRef.addValueEventListener(listener)
        awaitClose { approvedRef.removeEventListener(listener) }
    }

    /**
     * Admin: Get all pending reports
     */
    fun observePendingReports(): Flow<List<Report>> = callbackFlow {
        val query = pendingRef.orderByChild("status").equalTo(Report.STATUS_PENDING)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reports = snapshot.children.mapNotNull { child ->
                    child.getValue(Report::class.java)
                }.sortedByDescending { it.createdAt }
                trySend(reports)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    /**
     * Admin: Approve a report
     */
    suspend fun approveReport(reportId: String, adminNote: String, adminId: String): Result<Unit> {
        return try {
            val snapshot = pendingRef.child(reportId).get().await()
            val report = snapshot.getValue(Report::class.java)
                ?: throw Exception("Report not found")

            val approvedReport = report.copy(
                status = Report.STATUS_APPROVED,
                adminNote = adminNote,
                reviewedAt = System.currentTimeMillis(),
                reviewedBy = adminId
            )

            // Move to approved_reports
            approvedRef.child(reportId).setValue(approvedReport.toMap()).await()
            // Update status in pending
            pendingRef.child(reportId).child("status").setValue(Report.STATUS_APPROVED).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Admin: Reject a report
     */
    suspend fun rejectReport(reportId: String, adminNote: String, adminId: String): Result<Unit> {
        return try {
            pendingRef.child(reportId).updateChildren(
                mapOf(
                    "status" to Report.STATUS_REJECTED,
                    "adminNote" to adminNote,
                    "reviewedAt" to System.currentTimeMillis(),
                    "reviewedBy" to adminId
                )
            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
