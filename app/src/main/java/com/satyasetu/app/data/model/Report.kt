package com.satyasetu.app.data.model

/**
 * Report model for safe reporting of fraud/superstition
 * Reports go to pending_reports and require admin approval
 */
data class Report(
    val reportId: String = "",
    val userId: String = "",
    val userName: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = CATEGORY_FAKE_BABA,
    val location: String = "",
    val district: String = "",
    val state: String = "",
    val imageUrls: List<String> = emptyList(),
    val videoUrl: String = "",
    val status: String = STATUS_PENDING,
    val adminNote: String = "",
    val isAnonymous: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val reviewedAt: Long = 0L,
    val reviewedBy: String = ""
) {
    companion object {
        const val STATUS_PENDING = "pending"
        const val STATUS_APPROVED = "approved"
        const val STATUS_REJECTED = "rejected"
        const val STATUS_UNDER_REVIEW = "under_review"

        const val CATEGORY_FAKE_BABA = "fake_baba"
        const val CATEGORY_FRAUD_SCHEME = "fraud_scheme"
        const val CATEGORY_SUPERSTITION = "superstition"
        const val CATEGORY_FAKE_MEDICINE = "fake_medicine"
        const val CATEGORY_OTHER = "other"

        fun getCategoryResId(category: String): Int = when (category) {
            CATEGORY_FAKE_BABA -> com.satyasetu.app.R.string.cat_fake_baba
            CATEGORY_FRAUD_SCHEME -> com.satyasetu.app.R.string.cat_fraud_scheme
            CATEGORY_SUPERSTITION -> com.satyasetu.app.R.string.cat_superstition
            CATEGORY_FAKE_MEDICINE -> com.satyasetu.app.R.string.cat_fake_medicine
            else -> com.satyasetu.app.R.string.cat_other
        }

        fun getStatusResId(status: String): Int = when (status) {
            STATUS_PENDING -> com.satyasetu.app.R.string.status_pending
            STATUS_APPROVED -> com.satyasetu.app.R.string.status_approved
            STATUS_REJECTED -> com.satyasetu.app.R.string.status_rejected
            STATUS_UNDER_REVIEW -> com.satyasetu.app.R.string.status_under_review
            else -> com.satyasetu.app.R.string.status_pending
        }
    }

    fun toMap(): Map<String, Any?> = mapOf(
        "reportId" to reportId,
        "userId" to userId,
        "userName" to userName,
        "title" to title,
        "description" to description,
        "category" to category,
        "location" to location,
        "district" to district,
        "state" to state,
        "imageUrls" to imageUrls,
        "videoUrl" to videoUrl,
        "status" to status,
        "adminNote" to adminNote,
        "isAnonymous" to isAnonymous,
        "createdAt" to createdAt,
        "reviewedAt" to reviewedAt,
        "reviewedBy" to reviewedBy
    )
}
