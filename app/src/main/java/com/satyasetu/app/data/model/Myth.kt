package com.satyasetu.app.data.model

/**
 * Myth vs Fact model for "Sach Ya Galat" module
 */
data class Myth(
    val mythId: String = "",
    val myth: String = "",
    val fact: String = "",
    val category: String = CATEGORY_HEALTH,
    val explanation: String = "",
    val videoUrl: String = "",
    val imageUrl: String = "",
    val source: String = "",
    val language: String = "hi",
    val likes: Int = 0,
    val isVerified: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val CATEGORY_HEALTH = "health"
        const val CATEGORY_SCIENCE = "science"
        const val CATEGORY_RELIGION = "religion"
        const val CATEGORY_FARMING = "farming"
        const val CATEGORY_GENERAL = "general"

        fun getCategoryDisplayName(category: String): String = when (category) {
            CATEGORY_HEALTH -> "स्वास्थ्य"
            CATEGORY_SCIENCE -> "विज्ञान"
            CATEGORY_RELIGION -> "धार्मिक"
            CATEGORY_FARMING -> "खेती"
            CATEGORY_GENERAL -> "सामान्य"
            else -> category
        }
    }

    fun toMap(): Map<String, Any?> = mapOf(
        "mythId" to mythId,
        "myth" to myth,
        "fact" to fact,
        "category" to category,
        "explanation" to explanation,
        "videoUrl" to videoUrl,
        "imageUrl" to imageUrl,
        "source" to source,
        "language" to language,
        "likes" to likes,
        "isVerified" to isVerified,
        "createdAt" to createdAt
    )
}
