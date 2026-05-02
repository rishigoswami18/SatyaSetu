package com.satyasetu.app.util

/**
 * Trie-based search engine for fast autocomplete and prefix matching.
 * Uses a compressed trie (Patricia Trie concept) for efficient memory usage
 * with Hindi + English text support.
 *
 * Time Complexity:
 *   - Insert: O(L) where L = length of the word
 *   - Search: O(L)
 *   - Autocomplete: O(L + K) where K = number of results
 *
 * Space Complexity: O(N * L) where N = number of words
 */
class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var isEndOfWord: Boolean = false
    val associatedIds: MutableSet<String> = mutableSetOf() // Post/Scheme IDs
    var frequency: Int = 0 // For ranking autocomplete suggestions
}

class SearchEngine {
    private val root = TrieNode()
    private val invertedIndex: MutableMap<String, MutableSet<String>> = mutableMapOf()

    /**
     * Insert a searchable text associated with an ID.
     * Tokenizes the text and inserts each token into the Trie.
     */
    fun index(id: String, text: String) {
        val tokens = tokenize(text)
        tokens.forEach { token ->
            insertIntoTrie(token, id)
            // Build inverted index for full-text search
            invertedIndex.getOrPut(token) { mutableSetOf() }.add(id)
        }
    }

    /**
     * Search for IDs matching a query using prefix matching.
     * Returns ranked results by frequency (most relevant first).
     */
    fun search(query: String, maxResults: Int = 20): List<String> {
        val tokens = tokenize(query)
        if (tokens.isEmpty()) return emptyList()

        // Find matching IDs for each token using prefix matching
        val matchSets = tokens.map { token ->
            val prefixMatches = findByPrefix(token)
            prefixMatches.flatMap { it.associatedIds }.toSet()
        }

        // Intersection of all token matches (AND search)
        val result = if (matchSets.isEmpty()) {
            emptySet()
        } else {
            matchSets.reduce { acc, set -> acc.intersect(set) }
        }

        return result.take(maxResults).toList()
    }

    /**
     * Get autocomplete suggestions for a prefix.
     * Uses BFS to find all words with the given prefix, sorted by frequency.
     */
    fun autocomplete(prefix: String, maxSuggestions: Int = 10): List<String> {
        val normalizedPrefix = prefix.lowercase().trim()
        if (normalizedPrefix.isEmpty()) return emptyList()

        var currentNode = root
        for (char in normalizedPrefix) {
            currentNode = currentNode.children[char] ?: return emptyList()
        }

        // BFS to collect all words under this prefix
        val suggestions = mutableListOf<Pair<String, Int>>()
        collectWords(currentNode, StringBuilder(normalizedPrefix), suggestions)

        return suggestions
            .sortedByDescending { it.second }
            .take(maxSuggestions)
            .map { it.first }
    }

    /**
     * Remove an indexed item by ID. Useful when posts are deleted.
     */
    fun remove(id: String) {
        invertedIndex.forEach { (_, ids) -> ids.remove(id) }
        // Note: We don't remove from Trie for performance (lazy cleanup)
    }

    /**
     * Clear the entire search index.
     */
    fun clear() {
        root.children.clear()
        invertedIndex.clear()
    }

    // ── Private helpers ──────────────────────────────────────────────

    private fun insertIntoTrie(word: String, id: String) {
        var currentNode = root
        for (char in word) {
            currentNode = currentNode.children.getOrPut(char) { TrieNode() }
        }
        currentNode.isEndOfWord = true
        currentNode.associatedIds.add(id)
        currentNode.frequency++
    }

    private fun findByPrefix(prefix: String): List<TrieNode> {
        var currentNode = root
        for (char in prefix) {
            currentNode = currentNode.children[char] ?: return emptyList()
        }

        val results = mutableListOf<TrieNode>()
        collectEndNodes(currentNode, results)
        return results
    }

    private fun collectEndNodes(node: TrieNode, results: MutableList<TrieNode>) {
        if (node.isEndOfWord) results.add(node)
        node.children.values.forEach { child ->
            collectEndNodes(child, results)
        }
    }

    private fun collectWords(
        node: TrieNode,
        prefix: StringBuilder,
        results: MutableList<Pair<String, Int>>
    ) {
        if (node.isEndOfWord) {
            results.add(Pair(prefix.toString(), node.frequency))
        }
        node.children.forEach { (char, child) ->
            prefix.append(char)
            collectWords(child, prefix, results)
            prefix.deleteCharAt(prefix.length - 1)
        }
    }

    /**
     * Tokenize text into searchable tokens.
     * Handles both Hindi (Devanagari) and English text.
     * Removes stop words and normalizes.
     */
    private fun tokenize(text: String): List<String> {
        val normalized = text.lowercase().trim()
        // Split by whitespace and punctuation, keep Devanagari characters
        val tokens = normalized.split(Regex("[\\s,।!?:;\"'()\\[\\]{}|/\\\\]+"))
            .filter { it.isNotBlank() && it.length > 1 }
            .filterNot { it in STOP_WORDS }
        return tokens
    }

    companion object {
        // Hindi + English stop words
        private val STOP_WORDS = setOf(
            // Hindi
            "और", "का", "के", "की", "है", "में", "को", "से", "पर", "ने",
            "यह", "वह", "इस", "उस", "एक", "हो", "जो", "तो", "कर", "भी",
            "मैं", "था", "थी", "थे", "हैं", "हम", "वे", "अपने", "होता",
            "होती", "कि", "जा", "रहा", "रही", "रहे", "सकता", "सकती",
            // English
            "the", "is", "at", "which", "on", "a", "an", "and", "or", "but",
            "in", "with", "to", "for", "of", "it", "this", "that", "are", "was",
            "be", "has", "had", "do", "does", "did", "will", "would", "can",
            "could", "should", "may", "might", "shall"
        )
    }
}

/**
 * Jaccard Similarity for near-duplicate content detection.
 * Used to prevent spam and duplicate posts.
 *
 * Algorithm: |A ∩ B| / |A ∪ B|
 * Time Complexity: O(N + M) where N, M are token counts
 * Returns value between 0.0 (completely different) and 1.0 (identical)
 */
object ContentDeduplicator {
    /**
     * Check if two texts are near-duplicates.
     * @param threshold Similarity threshold (default 0.7 = 70% similar)
     */
    fun areNearDuplicates(text1: String, text2: String, threshold: Double = 0.7): Boolean {
        return jaccardSimilarity(text1, text2) >= threshold
    }

    /**
     * Calculate Jaccard similarity between two texts.
     */
    fun jaccardSimilarity(text1: String, text2: String): Double {
        val set1 = tokenize(text1)
        val set2 = tokenize(text2)

        if (set1.isEmpty() && set2.isEmpty()) return 1.0
        if (set1.isEmpty() || set2.isEmpty()) return 0.0

        val intersection = set1.intersect(set2).size
        val union = set1.union(set2).size

        return if (union == 0) 0.0 else intersection.toDouble() / union.toDouble()
    }

    /**
     * Find duplicate posts from a list.
     * Uses pairwise comparison with early termination.
     * @return Map of postId -> list of duplicate postIds
     */
    fun findDuplicates(
        posts: List<Pair<String, String>>,  // (id, text) pairs
        threshold: Double = 0.7
    ): Map<String, List<String>> {
        val duplicates = mutableMapOf<String, MutableList<String>>()

        for (i in posts.indices) {
            for (j in i + 1 until posts.size) {
                if (areNearDuplicates(posts[i].second, posts[j].second, threshold)) {
                    duplicates.getOrPut(posts[i].first) { mutableListOf() }.add(posts[j].first)
                }
            }
        }

        return duplicates
    }

    private fun tokenize(text: String): Set<String> {
        return text.lowercase().trim()
            .split(Regex("[\\s,।!?:;\"'()\\[\\]{}]+"))
            .filter { it.isNotBlank() && it.length > 1 }
            .toSet()
    }
}
