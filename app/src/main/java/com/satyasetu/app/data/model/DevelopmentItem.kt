package com.satyasetu.app.data.model

/**
 * Development tracker item for Netaji Tracker (neutral)
 */
data class DevelopmentItem(
    val itemId: String = "",
    val area: String = "",
    val district: String = "",
    val state: String = "",
    val category: String = CATEGORY_ROADS,
    val title: String = "",
    val description: String = "",
    val status: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis(),
    val source: String = "",
    val verifiedBy: String = ""
) {
    companion object {
        const val CATEGORY_ROADS = "roads"
        const val CATEGORY_SCHOOLS = "schools"
        const val CATEGORY_ELECTRICITY = "electricity"
        const val CATEGORY_WATER = "water"
        const val CATEGORY_HOSPITAL = "hospital"
        const val CATEGORY_INTERNET = "internet"

        fun getCategoryDisplayName(category: String): String = when (category) {
            CATEGORY_ROADS -> "सड़कें"
            CATEGORY_SCHOOLS -> "स्कूल"
            CATEGORY_ELECTRICITY -> "बिजली"
            CATEGORY_WATER -> "पानी"
            CATEGORY_HOSPITAL -> "अस्पताल"
            CATEGORY_INTERNET -> "इंटरनेट"
            else -> category
        }

        fun getCategoryIcon(category: String): String = when (category) {
            CATEGORY_ROADS -> "🛣️"
            CATEGORY_SCHOOLS -> "🏫"
            CATEGORY_ELECTRICITY -> "⚡"
            CATEGORY_WATER -> "💧"
            CATEGORY_HOSPITAL -> "🏥"
            CATEGORY_INTERNET -> "📶"
            else -> "📋"
        }
    }

    fun toMap(): Map<String, Any?> = mapOf(
        "itemId" to itemId,
        "area" to area,
        "district" to district,
        "state" to state,
        "category" to category,
        "title" to title,
        "description" to description,
        "status" to status,
        "lastUpdated" to lastUpdated,
        "source" to source,
        "verifiedBy" to verifiedBy
    )
}
