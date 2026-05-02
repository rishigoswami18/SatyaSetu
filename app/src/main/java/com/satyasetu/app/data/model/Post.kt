package com.satyasetu.app.data.model

/**
 * Post model for the village feed
 */
data class Post(
    val postId: String = "",
    val villageId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userImageUrl: String = "",
    val description: String = "",
    val mediaUrl: String = "",
    val isVideo: Boolean = false,
    val type: String = TYPE_AWARENESS,
    val likes: Int = 0,
    val commentCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        const val TYPE_ISSUE = "issue"
        const val TYPE_NEWS = "news"
        const val TYPE_AWARENESS = "awareness"
        
        fun getTypeDisplayName(type: String): String = when (type) {
            TYPE_ISSUE -> "समस्या"
            TYPE_NEWS -> "समाचार"
            TYPE_AWARENESS -> "जागरूकता"
            else -> type
        }
    }

    fun toMap(): Map<String, Any?> = mapOf(
        "postId" to postId,
        "villageId" to villageId,
        "userId" to userId,
        "userName" to userName,
        "userImageUrl" to userImageUrl,
        "description" to description,
        "mediaUrl" to mediaUrl,
        "isVideo" to isVideo,
        "type" to type,
        "likes" to likes,
        "commentCount" to commentCount,
        "timestamp" to timestamp
    )
}
