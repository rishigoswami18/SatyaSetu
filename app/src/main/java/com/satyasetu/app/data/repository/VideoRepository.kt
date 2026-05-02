package com.satyasetu.app.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.satyasetu.app.data.model.Video
import com.satyasetu.app.util.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class VideoRepository @Inject constructor(
    private val database: FirebaseDatabase
) {
    private val videosRef = database.reference.child(Constants.DB_VIDEOS)

    fun observeVideos(): Flow<List<Video>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val videos = snapshot.children.mapNotNull { child ->
                    child.getValue(Video::class.java)
                }.sortedByDescending { it.createdAt }
                trySend(videos)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        videosRef.addValueEventListener(listener)
        awaitClose { videosRef.removeEventListener(listener) }
    }

    fun observeVideosByCategory(category: String): Flow<List<Video>> = callbackFlow {
        val query = videosRef.orderByChild("category").equalTo(category)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val videos = snapshot.children.mapNotNull { child ->
                    child.getValue(Video::class.java)
                }.sortedByDescending { it.createdAt }
                trySend(videos)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun likeVideo(videoId: String): Result<Unit> {
        return try {
            incrementCounter(videosRef.child(videoId).child("likes"), "Unable to update video likes.")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun incrementViews(videoId: String): Result<Unit> {
        return try {
            incrementCounter(videosRef.child(videoId).child("views"), "Unable to update video views.")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveVideoForUser(userId: String, videoId: String): Result<Unit> {
        return try {
            database.reference
                .child(Constants.DB_USERS)
                .child(userId)
                .child("savedVideos")
                .child(videoId)
                .setValue(true)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun seedSampleVideos() {
        val sampleVideos = listOf(
            Video(
                videoId = "v1",
                title = "à¤¹à¤¾à¤¥ à¤§à¥‹à¤¨à¥‡ à¤•à¤¾ à¤¸à¤¹à¥€ à¤¤à¤°à¥€à¤•à¤¾",
                description = "à¤œà¤¾à¤¨à¤¿à¤ à¤¹à¤¾à¤¥ à¤§à¥‹à¤¨à¥‡ à¤¸à¥‡ à¤•à¥ˆà¤¸à¥‡ à¤¬à¥€à¤®à¤¾à¤°à¤¿à¤¯à¥‹à¤‚ à¤¸à¥‡ à¤¬à¤šà¤¾ à¤œà¤¾ à¤¸à¤•à¤¤à¤¾ à¤¹à¥ˆ",
                category = Video.CATEGORY_HEALTH,
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                thumbnailUrl = "",
                likes = 245,
                views = 1200,
                tags = listOf("à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯", "à¤¸à¥à¤µà¤šà¥à¤›à¤¤à¤¾")
            ),
            Video(
                videoId = "v2",
                title = "PM à¤•à¤¿à¤¸à¤¾à¤¨ à¤¯à¥‹à¤œà¤¨à¤¾ - à¤•à¥ˆà¤¸à¥‡ à¤•à¤°à¥‡à¤‚ à¤†à¤µà¥‡à¤¦à¤¨",
                description = "PM à¤•à¤¿à¤¸à¤¾à¤¨ à¤¸à¤®à¥à¤®à¤¾à¤¨ à¤¨à¤¿à¤§à¤¿ à¤¯à¥‹à¤œà¤¨à¤¾ à¤•à¥‡ à¤²à¤¿à¤ à¤‘à¤¨à¤²à¤¾à¤‡à¤¨ à¤†à¤µà¥‡à¤¦à¤¨ à¤•à¥ˆà¤¸à¥‡ à¤•à¤°à¥‡à¤‚",
                category = Video.CATEGORY_GOVERNMENT,
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                thumbnailUrl = "",
                likes = 567,
                views = 3400,
                tags = listOf("à¤¸à¤°à¤•à¤¾à¤°à¥€ à¤¯à¥‹à¤œà¤¨à¤¾", "à¤•à¤¿à¤¸à¤¾à¤¨")
            ),
            Video(
                videoId = "v3",
                title = "à¤œà¥ˆà¤µà¤¿à¤• à¤–à¥‡à¤¤à¥€ à¤•à¥ˆà¤¸à¥‡ à¤•à¤°à¥‡à¤‚",
                description = "à¤•à¤® à¤²à¤¾à¤—à¤¤ à¤®à¥‡à¤‚ à¤œà¥ˆà¤µà¤¿à¤• à¤–à¥‡à¤¤à¥€ à¤•à¤°à¤¨à¥‡ à¤•à¥‡ à¤†à¤¸à¤¾à¤¨ à¤¤à¤°à¥€à¤•à¥‡",
                category = Video.CATEGORY_FARMING,
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                thumbnailUrl = "",
                likes = 389,
                views = 2100,
                tags = listOf("à¤–à¥‡à¤¤à¥€", "à¤œà¥ˆà¤µà¤¿à¤•")
            ),
            Video(
                videoId = "v4",
                title = "à¤¬à¤¿à¤œà¤²à¥€ à¤•à¥ˆà¤¸à¥‡ à¤¬à¤¨à¤¤à¥€ à¤¹à¥ˆ - à¤¸à¤°à¤² à¤µà¤¿à¤œà¥à¤žà¤¾à¤¨",
                description = "à¤¬à¤¿à¤œà¤²à¥€ à¤¬à¤¨à¤¨à¥‡ à¤•à¥€ à¤ªà¥à¤°à¤•à¥à¤°à¤¿à¤¯à¤¾ à¤•à¥‹ à¤¸à¤°à¤² à¤­à¤¾à¤·à¤¾ à¤®à¥‡à¤‚ à¤¸à¤®à¤à¤¿à¤",
                category = Video.CATEGORY_SCIENCE,
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                thumbnailUrl = "",
                likes = 198,
                views = 890,
                tags = listOf("à¤µà¤¿à¤œà¥à¤žà¤¾à¤¨", "à¤¬à¤¿à¤œà¤²à¥€")
            ),
            Video(
                videoId = "v5",
                title = "à¤‘à¤¨à¤²à¤¾à¤‡à¤¨ à¤§à¥‹à¤–à¤¾à¤§à¤¡à¤¼à¥€ à¤¸à¥‡ à¤•à¥ˆà¤¸à¥‡ à¤¬à¤šà¥‡à¤‚",
                description = "à¤«à¥‹à¤¨ à¤ªà¤° à¤†à¤¨à¥‡ à¤µà¤¾à¤²à¥‡ à¤«à¥à¤°à¥‰à¤¡ à¤•à¥‰à¤²à¥à¤¸ à¤¸à¥‡ à¤•à¥ˆà¤¸à¥‡ à¤¬à¤šà¥‡à¤‚",
                category = Video.CATEGORY_DIGITAL_SAFETY,
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                thumbnailUrl = "",
                likes = 723,
                views = 4500,
                tags = listOf("à¤¡à¤¿à¤œà¤¿à¤Ÿà¤² à¤¸à¥à¤°à¤•à¥à¤·à¤¾", "à¤§à¥‹à¤–à¤¾à¤§à¤¡à¤¼à¥€")
            )
        )

        sampleVideos.forEach { video ->
            videosRef.child(video.videoId).setValue(video.toMap())
        }
    }

    private suspend fun incrementCounter(reference: DatabaseReference, failureMessage: String) {
        suspendCancellableCoroutine { continuation ->
            reference.runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val currentValue = currentData.getValue(Int::class.java) ?: 0
                    currentData.value = currentValue + 1
                    return Transaction.success(currentData)
                }

                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    when {
                        error != null -> continuation.resumeWithException(error.toException())
                        committed -> continuation.resume(Unit)
                        else -> continuation.resumeWithException(IllegalStateException(failureMessage))
                    }
                }
            })
        }
    }
}
