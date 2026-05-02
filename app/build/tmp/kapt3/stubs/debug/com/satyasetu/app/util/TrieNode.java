package com.satyasetu.app.util;

/**
 * Trie-based search engine for fast autocomplete and prefix matching.
 * Uses a compressed trie (Patricia Trie concept) for efficient memory usage
 * with Hindi + English text support.
 *
 * Time Complexity:
 *  - Insert: O(L) where L = length of the word
 *  - Search: O(L)
 *  - Autocomplete: O(L + K) where K = number of results
 *
 * Space Complexity: O(N * L) where N = number of words
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00000\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/satyasetu/app/util/TrieNode;", "", "()V", "associatedIds", "", "", "getAssociatedIds", "()Ljava/util/Set;", "children", "", "", "getChildren", "()Ljava/util/Map;", "frequency", "", "getFrequency", "()I", "setFrequency", "(I)V", "isEndOfWord", "", "()Z", "setEndOfWord", "(Z)V", "app_debug"})
public final class TrieNode {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Character, com.satyasetu.app.util.TrieNode> children = null;
    private boolean isEndOfWord = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> associatedIds = null;
    private int frequency = 0;
    
    public TrieNode() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.Character, com.satyasetu.app.util.TrieNode> getChildren() {
        return null;
    }
    
    public final boolean isEndOfWord() {
        return false;
    }
    
    public final void setEndOfWord(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getAssociatedIds() {
        return null;
    }
    
    public final int getFrequency() {
        return 0;
    }
    
    public final void setFrequency(int p0) {
    }
}