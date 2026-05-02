package com.satyasetu.app.util

import com.satyasetu.app.data.model.Post
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Smart post ranking engine using proven algorithms.
 *
 * Combines:
 * 1. Wilson Score Interval - For confidence-weighted like ranking (Reddit-style)
 * 2. Time Decay - Newer posts get a boost (Hacker News-style)
 * 3. Engagement Score - Comments + Likes combined metric
 * 4. Trending Detection - Sliding window for viral content
 */
object PostRankingEngine {

    /**
     * Sort posts using the combined ranking algorithm.
     * This is the main entry point for the feed.
     *
     * Algorithm Overview:
     * finalScore = wilsonScore * 0.3 + timeDecayScore * 0.4 + engagementScore * 0.2 + trendingBoost * 0.1
     *
     * Time Complexity: O(N log N) where N = number of posts
     */
    fun rankPosts(posts: List<Post>, currentTimeMs: Long = System.currentTimeMillis()): List<Post> {
        if (posts.isEmpty()) return posts

        // Calculate scores for all posts
        val scoredPosts = posts.map { post ->
            val wilson = wilsonScore(post.likes, post.likes + post.commentCount)
            val timeDecay = timeDecayScore(post.timestamp, currentTimeMs)
            val engagement = engagementScore(post.likes, post.commentCount)
            val trending = trendingBoost(post, currentTimeMs)

            // Weighted combination
            val finalScore = wilson * 0.3 + timeDecay * 0.4 + engagement * 0.2 + trending * 0.1

            ScoredPost(post, finalScore)
        }

        // Sort by score descending (highest first)
        return scoredPosts
            .sortedByDescending { it.score }
            .map { it.post }
    }

    /**
     * Wilson Score Confidence Interval (Lower Bound)
     *
     * Used by Reddit to rank comments. Balances between:
     * - Items with many positive votes (popular)
     * - Items with fewer but proportionally positive votes (quality)
     *
     * Formula: (p̂ + z²/2n - z * √(p̂(1-p̂)/n + z²/4n²)) / (1 + z²/n)
     * where p̂ = positive/total, z = 1.96 for 95% confidence
     *
     * @param positive Number of positive interactions (likes)
     * @param total Total interactions
     * @return Score between 0.0 and 1.0
     */
    fun wilsonScore(positive: Int, total: Int): Double {
        if (total == 0) return 0.0

        val z = 1.96 // 95% confidence
        val phat = positive.toDouble() / total.toDouble()
        val n = total.toDouble()

        val numerator = phat + (z * z) / (2.0 * n) -
            z * sqrt((phat * (1.0 - phat) + (z * z) / (4.0 * n)) / n)
        val denominator = 1.0 + (z * z) / n

        return max(0.0, numerator / denominator)
    }

    /**
     * Time Decay Score (Hacker News-style)
     *
     * Uses logarithmic decay so that:
     * - Very recent posts (< 1 hour) get highest boost
     * - Posts decay gradually over time
     * - Old posts still have some relevance
     *
     * Formula: 1 / (1 + log(1 + hoursAge))
     * 
     * @return Score between 0.0 and 1.0
     */
    fun timeDecayScore(postTimestamp: Long, currentTimeMs: Long): Double {
        val ageHours = (currentTimeMs - postTimestamp).toDouble() / (1000.0 * 60 * 60)
        if (ageHours < 0) return 1.0 // Future post (clock issue)

        return 1.0 / (1.0 + ln(1.0 + ageHours))
    }

    /**
     * Engagement Score
     *
     * Normalized composite of likes and comments.
     * Comments are weighted 2x more than likes (deeper engagement).
     *
     * Formula: log(1 + likes + 2 * comments) / log(1 + MAX_ENGAGEMENT)
     *
     * @return Score between 0.0 and 1.0
     */
    fun engagementScore(likes: Int, comments: Int): Double {
        val maxEngagement = 1000.0 // Normalization constant
        val rawScore = likes.toDouble() + 2.0 * comments.toDouble()
        return ln(1.0 + rawScore) / ln(1.0 + maxEngagement)
    }

    /**
     * Trending Boost
     *
     * Detects posts that are getting high engagement in a short time window.
     * Uses engagement velocity: engagement per hour since posting.
     *
     * A post is "trending" if its velocity exceeds the threshold.
     *
     * @return Boost value between 0.0 and 1.0
     */
    fun trendingBoost(post: Post, currentTimeMs: Long): Double {
        val ageHours = max(1.0, (currentTimeMs - post.timestamp).toDouble() / (1000.0 * 60 * 60))
        val totalEngagement = post.likes + post.commentCount * 2

        val velocity = totalEngagement.toDouble() / ageHours

        // Velocity thresholds for trending
        return when {
            velocity > 50 -> 1.0   // 🔥 Viral
            velocity > 20 -> 0.7   // 📈 Trending
            velocity > 10 -> 0.4   // 📊 Growing
            velocity > 5 -> 0.2    // 📌 Active
            else -> 0.0            // Normal
        }
    }

    /**
     * Get trending posts (engagement velocity > threshold)
     *
     * @param posts All posts
     * @param windowHours Time window to consider (default 24 hours)
     * @param minVelocity Minimum engagement velocity to be "trending"
     * @return Trending posts sorted by velocity
     */
    fun getTrendingPosts(
        posts: List<Post>,
        currentTimeMs: Long = System.currentTimeMillis(),
        windowHours: Int = 24,
        minVelocity: Double = 5.0
    ): List<Post> {
        val windowMs = windowHours.toLong() * 60 * 60 * 1000

        return posts
            .filter { currentTimeMs - it.timestamp < windowMs }
            .map { post ->
                val ageHours = max(1.0, (currentTimeMs - post.timestamp).toDouble() / (1000.0 * 60 * 60))
                val velocity = (post.likes + post.commentCount * 2).toDouble() / ageHours
                Pair(post, velocity)
            }
            .filter { it.second >= minVelocity }
            .sortedByDescending { it.second }
            .map { it.first }
    }

    /**
     * Filter posts by type with smart ordering.
     * Issues are boosted if they have high engagement (community cares about them).
     */
    fun filterAndRank(posts: List<Post>, type: String?): List<Post> {
        val filtered = if (type != null) posts.filter { it.type == type } else posts
        return rankPosts(filtered)
    }

    private data class ScoredPost(val post: Post, val score: Double)
}

/**
 * Content type classifier using keyword matching with weighted scoring.
 * Simple but effective for Hindi content categorization.
 *
 * Uses a HashMap-based approach for O(T) classification
 * where T = number of tokens in the text.
 */
object ContentClassifier {

    private val categoryKeywords = mapOf(
        "health" to listOf(
            "बुखार", "सर्दी", "खांसी", "दवाई", "डॉक्टर", "अस्पताल", "इलाज",
            "बीमारी", "स्वास्थ्य", "टीका", "टीकाकरण", "पोषण", "दस्त",
            "मलेरिया", "डेंगू", "covid", "health", "medicine", "fever"
        ),
        "education" to listOf(
            "स्कूल", "पढ़ाई", "शिक्षा", "परीक्षा", "छात्रवृत्ति", "किताब",
            "कक्षा", "विद्यालय", "शिक्षक", "पाठ", "school", "education"
        ),
        "farming" to listOf(
            "खेती", "फसल", "किसान", "बीज", "खाद", "सिंचाई", "कृषि",
            "मंडी", "गेहूं", "धान", "गन्ना", "मिट्टी", "farming", "crop"
        ),
        "government" to listOf(
            "योजना", "सरकार", "सरकारी", "आधार", "राशन", "पेंशन", "मुफ्त",
            "अनुदान", "scheme", "government", "subsidy", "pmkisan"
        ),
        "digital_safety" to listOf(
            "फ्रॉड", "ठगी", "ओटीपी", "बैंक", "साइबर", "हैक", "पासवर्ड",
            "लिंक", "फेक", "ऑनलाइन", "fraud", "otp", "scam", "hack"
        ),
        "superstition" to listOf(
            "अंधविश्वास", "भूत", "प्रेत", "जादू", "टोना", "तांत्रिक",
            "मंत्र", "जिन्न", "शकुन", "अपशकुन", "superstition"
        )
    )

    /**
     * Classify content into a category.
     * @return Pair of (category, confidence 0.0-1.0)
     */
    fun classify(text: String): Pair<String, Double> {
        val tokens = text.lowercase().split(Regex("[\\s,।!?:;]+"))
            .filter { it.isNotBlank() }
            .toSet()

        var bestCategory = "general"
        var bestScore = 0.0

        categoryKeywords.forEach { (category, keywords) ->
            val matchCount = keywords.count { keyword -> tokens.any { it.contains(keyword) } }
            val score = matchCount.toDouble() / keywords.size.toDouble()
            if (score > bestScore) {
                bestScore = score
                bestCategory = category
            }
        }

        return Pair(bestCategory, bestScore)
    }
}
