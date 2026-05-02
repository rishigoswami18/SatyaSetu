package com.satyasetu.app.data.repository

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.satyasetu.app.data.model.Post
import com.satyasetu.app.util.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class PostRepository @Inject constructor(
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
) {
    private val postsRef = database.reference.child(Constants.DB_POSTS)

    fun observeVillagePosts(villageId: String): Flow<List<Post>> = callbackFlow {
        val query = postsRef.orderByChild("villageId").equalTo(villageId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = snapshot.children.mapNotNull { it.getValue(Post::class.java) }
                    .sortedByDescending { it.timestamp }
                trySend(posts)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun createPost(
        villageId: String,
        userId: String,
        userName: String,
        description: String,
        mediaUri: Uri?,
        isVideo: Boolean,
        type: String
    ): Result<String> {
        return try {
            val postId = postsRef.push().key ?: UUID.randomUUID().toString()
            var mediaUrl = ""

            if (mediaUri != null) {
                val extension = if (isVideo) ".mp4" else ".jpg"
                val fileName = "post_${UUID.randomUUID()}$extension"
                val storageRef = storage.reference
                    .child(Constants.STORAGE_POST_MEDIA)
                    .child(fileName)

                storageRef.putFile(mediaUri).await()
                mediaUrl = storageRef.downloadUrl.await().toString()
            }

            val post = Post(
                postId = postId,
                villageId = villageId,
                userId = userId,
                userName = userName,
                description = description,
                mediaUrl = mediaUrl,
                isVideo = isVideo,
                type = type
            )

            postsRef.child(postId).setValue(post.toMap()).await()
            Result.success(postId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun likePost(postId: String): Result<Unit> {
        return try {
            incrementCounter(postsRef.child(postId).child("likes"))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun incrementCounter(reference: DatabaseReference) {
        suspendCancellableCoroutine { continuation ->
            reference.runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val currentLikes = currentData.getValue(Int::class.java) ?: 0
                    currentData.value = currentLikes + 1
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
                        else -> continuation.resumeWithException(
                            IllegalStateException("Unable to update post likes.")
                        )
                    }
                }
            })
        }
    }
}
