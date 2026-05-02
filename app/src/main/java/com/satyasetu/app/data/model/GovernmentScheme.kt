package com.satyasetu.app.data.model

/**
 * Model for Government Schemes (Sarkari Yojana)
 * Provides curated information about Indian government schemes
 * that users can browse and learn about.
 */
data class GovernmentScheme(
    val id: String = "",
    val nameHindi: String = "",
    val nameEnglish: String = "",
    val descriptionHindi: String = "",
    val descriptionEnglish: String = "",
    val eligibilityHindi: String = "",
    val eligibilityEnglish: String = "",
    val benefitHindi: String = "",
    val benefitEnglish: String = "",
    val howToApplyHindi: String = "",
    val howToApplyEnglish: String = "",
    val websiteUrl: String = "",
    val category: String = CATEGORY_GENERAL,
    val isActive: Boolean = true
) {
    companion object {
        const val CATEGORY_FARMING = "farming"
        const val CATEGORY_HEALTH = "health"
        const val CATEGORY_EDUCATION = "education"
        const val CATEGORY_HOUSING = "housing"
        const val CATEGORY_FINANCIAL = "financial"
        const val CATEGORY_WOMEN = "women"
        const val CATEGORY_GENERAL = "general"

        fun getCategoryDisplayNameHindi(category: String): String = when (category) {
            CATEGORY_FARMING -> "कृषि"
            CATEGORY_HEALTH -> "स्वास्थ्य"
            CATEGORY_EDUCATION -> "शिक्षा"
            CATEGORY_HOUSING -> "आवास"
            CATEGORY_FINANCIAL -> "वित्तीय"
            CATEGORY_WOMEN -> "महिला"
            CATEGORY_GENERAL -> "सामान्य"
            else -> category
        }

        fun getCategoryDisplayNameEnglish(category: String): String = when (category) {
            CATEGORY_FARMING -> "Agriculture"
            CATEGORY_HEALTH -> "Health"
            CATEGORY_EDUCATION -> "Education"
            CATEGORY_HOUSING -> "Housing"
            CATEGORY_FINANCIAL -> "Financial"
            CATEGORY_WOMEN -> "Women"
            CATEGORY_GENERAL -> "General"
            else -> category
        }
    }
}

/**
 * AI-generated educational content templates.
 * Used for bootstrap content and daily knowledge feed.
 */
data class EducationalContent(
    val id: String = "",
    val titleHindi: String = "",
    val titleEnglish: String = "",
    val contentHindi: String = "",
    val contentEnglish: String = "",
    val category: String = "",
    val source: String = "SatyaSetu Team",
    val isVerified: Boolean = true
)
