package com.satyasetu.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Extension functions used throughout the app
 */

/**
 * Format timestamp to readable Hindi date string
 */
fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale("hi", "IN"))
    return sdf.format(Date(this))
}

/**
 * Format timestamp to relative time (e.g., "2 घंटे पहले")
 */
fun Long.toRelativeTime(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "अभी"
        diff < TimeUnit.HOURS.toMillis(1) -> {
            val mins = TimeUnit.MILLISECONDS.toMinutes(diff)
            "$mins मिनट पहले"
        }
        diff < TimeUnit.DAYS.toMillis(1) -> {
            val hours = TimeUnit.MILLISECONDS.toHours(diff)
            "$hours घंटे पहले"
        }
        diff < TimeUnit.DAYS.toMillis(7) -> {
            val days = TimeUnit.MILLISECONDS.toDays(diff)
            "$days दिन पहले"
        }
        diff < TimeUnit.DAYS.toMillis(30) -> {
            val weeks = TimeUnit.MILLISECONDS.toDays(diff) / 7
            "$weeks हफ्ते पहले"
        }
        else -> toFormattedDate()
    }
}

/**
 * Format number to compact string (e.g., 1.2K, 3.4M)
 */
fun Int.toCompactString(): String {
    return when {
        this < 1000 -> this.toString()
        this < 1_000_000 -> String.format("%.1fK", this / 1000.0)
        else -> String.format("%.1fM", this / 1_000_000.0)
    }
}

/**
 * Validate email format
 */
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Validate Indian phone number
 */
fun String.isValidIndianPhone(): Boolean {
    val cleaned = this.replace("[^0-9]".toRegex(), "")
    return cleaned.length == 10 && cleaned.first() in '6'..'9'
}

/**
 * Truncate string with ellipsis
 */
fun String.truncate(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.take(maxLength - 3) + "..."
    } else {
        this
    }
}
