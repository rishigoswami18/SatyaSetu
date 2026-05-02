package com.satyasetu.app.data.model

/**
 * Learning module lesson model
 */
data class Lesson(
    val lessonId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = CATEGORY_HEALTH,
    val content: String = "",
    val imageUrl: String = "",
    val videoUrl: String = "",
    val order: Int = 0,
    val estimatedMinutes: Int = 5,
    val language: String = "hi",
    val quiz: List<QuizQuestion> = emptyList(),
    val isPublished: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val CATEGORY_HEALTH = "health_basics"
        const val CATEGORY_DIGITAL_FRAUD = "digital_fraud"
        const val CATEGORY_VOTING = "voting_rights"
        const val CATEGORY_SCIENCE = "basic_science"
        const val CATEGORY_LEGAL = "legal_awareness"

        fun getCategoryDisplayName(category: String): String = when (category) {
            CATEGORY_HEALTH -> "स्वास्थ्य की बुनियाद"
            CATEGORY_DIGITAL_FRAUD -> "डिजिटल धोखाधड़ी"
            CATEGORY_VOTING -> "मतदान अधिकार"
            CATEGORY_SCIENCE -> "बुनियादी विज्ञान"
            CATEGORY_LEGAL -> "कानूनी जागरूकता"
            else -> category
        }

        fun getCategoryIcon(category: String): String = when (category) {
            CATEGORY_HEALTH -> "🏥"
            CATEGORY_DIGITAL_FRAUD -> "🛡️"
            CATEGORY_VOTING -> "🗳️"
            CATEGORY_SCIENCE -> "🔬"
            CATEGORY_LEGAL -> "⚖️"
            else -> "📚"
        }
    }

    fun toMap(): Map<String, Any?> = mapOf(
        "lessonId" to lessonId,
        "title" to title,
        "description" to description,
        "category" to category,
        "content" to content,
        "imageUrl" to imageUrl,
        "videoUrl" to videoUrl,
        "order" to order,
        "estimatedMinutes" to estimatedMinutes,
        "language" to language,
        "quiz" to quiz.map { it.toMap() },
        "isPublished" to isPublished,
        "createdAt" to createdAt
    )
}

/**
 * Quiz question within a lesson
 */
data class QuizQuestion(
    val questionId: String = "",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctOptionIndex: Int = 0,
    val explanation: String = ""
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "questionId" to questionId,
        "question" to question,
        "options" to options,
        "correctOptionIndex" to correctOptionIndex,
        "explanation" to explanation
    )
}
