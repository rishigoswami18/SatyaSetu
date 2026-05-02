package com.satyasetu.app.data.model

/**
 * Video content model for the short video feed
 */
data class Video(
    val videoId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = CATEGORY_HEALTH,
    val url: String = "",
    val thumbnailUrl: String = "",
    val uploaderName: String = "SatyaSetu Team",
    val uploaderId: String = "",
    val likes: Int = 0,
    val views: Int = 0,
    val shares: Int = 0,
    val duration: Long = 0L,
    val tags: List<String> = emptyList(),
    val language: String = "hi",
    val isVerified: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val CATEGORY_HEALTH = "health"
        const val CATEGORY_SCIENCE = "science"
        const val CATEGORY_FARMING = "farming"
        const val CATEGORY_GOVERNMENT = "government_schemes"
        const val CATEGORY_EDUCATION = "education"
        const val CATEGORY_DIGITAL_SAFETY = "digital_safety"

        fun getAllCategories(): List<String> = listOf(
            CATEGORY_HEALTH,
            CATEGORY_SCIENCE,
            CATEGORY_FARMING,
            CATEGORY_GOVERNMENT,
            CATEGORY_EDUCATION,
            CATEGORY_DIGITAL_SAFETY
        )

        fun getCategoryDisplayName(category: String): String = when (category) {
            CATEGORY_HEALTH -> "स्वास्थ्य"
            CATEGORY_SCIENCE -> "विज्ञान"
            CATEGORY_FARMING -> "खेती"
            CATEGORY_GOVERNMENT -> "सरकारी योजनाएँ"
            CATEGORY_EDUCATION -> "शिक्षा"
            CATEGORY_DIGITAL_SAFETY -> "डिजिटल सुरक्षा"
            else -> category
        }
    }

    fun toMap(): Map<String, Any?> = mapOf(
        "videoId" to videoId,
        "title" to title,
        "description" to description,
        "category" to category,
        "url" to url,
        "thumbnailUrl" to thumbnailUrl,
        "uploaderName" to uploaderName,
        "uploaderId" to uploaderId,
        "likes" to likes,
        "views" to views,
        "shares" to shares,
        "duration" to duration,
        "tags" to tags,
        "language" to language,
        "isVerified" to isVerified,
        "createdAt" to createdAt
    )
}
