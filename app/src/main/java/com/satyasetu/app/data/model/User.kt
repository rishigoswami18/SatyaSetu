package com.satyasetu.app.data.model

/**
 * User profile model for SatyaSetu
 */
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val villageId: String = "",
    val villageName: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val district: String = "",
    val state: String = "",
    val role: String = ROLE_USER,
    val preferredLanguage: String = LANG_HINDI,
    val profileImageUrl: String = "",
    val lessonsCompleted: List<String> = emptyList(),
    val quizScores: Map<String, Int> = emptyMap(),
    val savedVideos: List<String> = emptyList(),
    val joinedAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val ROLE_USER = "user"
        const val ROLE_ADMIN = "admin"
        const val ROLE_MODERATOR = "moderator"
        const val LANG_HINDI = "hi"
        const val LANG_ENGLISH = "en"
    }

    /**
     * Convert to Firebase-compatible Map
     */
    fun toMap(): Map<String, Any?> = mapOf(
        "uid" to uid,
        "name" to name,
        "email" to email,
        "phone" to phone,
        "villageId" to villageId,
        "villageName" to villageName,
        "lat" to lat,
        "lng" to lng,
        "district" to district,
        "state" to state,
        "role" to role,
        "preferredLanguage" to preferredLanguage,
        "profileImageUrl" to profileImageUrl,
        "lessonsCompleted" to lessonsCompleted,
        "quizScores" to quizScores,
        "savedVideos" to savedVideos,
        "joinedAt" to joinedAt
    )
}
