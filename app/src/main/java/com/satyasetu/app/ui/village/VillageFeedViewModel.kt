package com.satyasetu.app.ui.village

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.Post
import com.satyasetu.app.data.repository.AuthRepository
import com.satyasetu.app.data.repository.BootstrapRepository
import com.satyasetu.app.data.repository.PostRepository
import com.satyasetu.app.data.repository.VillageRepository
import com.satyasetu.app.util.PostRankingEngine
import com.satyasetu.app.util.SearchEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VillageFeedUiState(
    val posts: List<Post> = emptyList(),
    val trendingPosts: List<Post> = emptyList(),
    val villageName: String = "My Village",
    val villageId: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedFilter: String? = null, // null = all, or Post.TYPE_*
    val isSeeding: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Post> = emptyList()
)

@HiltViewModel
class VillageFeedViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val villageRepository: VillageRepository,
    private val authRepository: AuthRepository,
    private val bootstrapRepository: BootstrapRepository,
    private val localeManager: com.satyasetu.app.util.LocaleManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(VillageFeedUiState())
    val uiState: StateFlow<VillageFeedUiState> = _uiState.asStateFlow()

    // Trie-based search engine for fast post searching
    private val searchEngine = SearchEngine()

    init {
        loadVillageFeed()
    }

    private fun loadVillageFeed() {
        viewModelScope.launch {
            val user = authRepository.currentUser
            if (user == null) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Please log in to view your village feed.")
                }
                return@launch
            }

            var villageId = villageRepository.getCachedVillageId()
            var villageName = villageRepository.getCachedVillageName()

            if (villageId == null || villageName == null) {
                authRepository.getUserProfile(user.uid)
                    .onSuccess { profile ->
                        val profileVillageId = profile.villageId.takeIf { it.isNotBlank() }
                        val profileVillageName = profile.villageName.takeIf { it.isNotBlank() }

                        villageId = profileVillageId
                        villageName = profileVillageName

                        if (profileVillageId != null && profileVillageName != null) {
                            villageRepository.cacheVillage(profileVillageId, profileVillageName)
                        }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(isLoading = false, error = error.message ?: "Unable to load village.")
                        }
                    }
            }

            val finalVillageId = villageId
            if (finalVillageId == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Select your village to start the community feed."
                    )
                }
                return@launch
            }

            _uiState.update {
                it.copy(
                    villageId = finalVillageId,
                    villageName = villageName ?: "My Village",
                    error = null
                )
            }

            // Seed bootstrap data if this is the first time
            seedBootstrapData(finalVillageId)

            // Observe posts with smart ranking
            postRepository.observeVillagePosts(finalVillageId)
                .catch { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message ?: "Unable to load posts.")
                    }
                }
                .collect { posts ->
                    // Apply DSA-based ranking algorithm
                    val rankedPosts = PostRankingEngine.rankPosts(posts)
                    val trendingPosts = PostRankingEngine.getTrendingPosts(posts)

                    // Build search index
                    searchEngine.clear()
                    posts.forEach { post ->
                        searchEngine.index(post.postId, "${post.description} ${post.userName}")
                    }

                    // Apply filter if set
                    val filteredPosts = applyFilter(rankedPosts, _uiState.value.selectedFilter)

                    _uiState.update {
                        it.copy(
                            posts = filteredPosts,
                            trendingPosts = trendingPosts,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    /**
     * Seed bootstrap data on first launch.
     * Makes the app feel alive from day 1.
     */
    private suspend fun seedBootstrapData(villageId: String) {
        try {
            if (!bootstrapRepository.isSeeded()) {
                _uiState.update { it.copy(isSeeding = true) }
                bootstrapRepository.seedData(villageId)
                _uiState.update { it.copy(isSeeding = false) }
            }
        } catch (_: Exception) {
            _uiState.update { it.copy(isSeeding = false) }
        }
    }

    /**
     * Filter posts by type using the ranking engine.
     */
    fun setFilter(type: String?) {
        _uiState.update { currentState ->
            val filter = if (currentState.selectedFilter == type) null else type
            currentState.copy(
                selectedFilter = filter,
                posts = applyFilter(currentState.posts, filter)
            )
        }
    }

    /**
     * Search posts using Trie-based search engine.
     * O(L + K) where L = query length, K = result count
     */
    fun searchPosts(query: String) {
        _uiState.update { currentState ->
            if (query.isBlank()) {
                currentState.copy(searchQuery = "", searchResults = emptyList())
            } else {
                val matchingIds = searchEngine.search(query)
                val results = currentState.posts.filter { it.postId in matchingIds }
                currentState.copy(searchQuery = query, searchResults = results)
            }
        }
    }

    fun likePost(postId: String) {
        viewModelScope.launch {
            postRepository.likePost(postId)
        }
    }

    fun toggleLanguage() {
        viewModelScope.launch {
            val current = localeManager.getCurrentLanguage()
            val next = if (current == com.satyasetu.app.util.LocaleManager.LANG_HINDI) {
                com.satyasetu.app.util.LocaleManager.LANG_ENGLISH
            } else {
                com.satyasetu.app.util.LocaleManager.LANG_HINDI
            }
            localeManager.setLanguage(next)
        }
    }

    private fun applyFilter(posts: List<Post>, filter: String?): List<Post> {
        return if (filter != null) {
            PostRankingEngine.filterAndRank(posts, filter)
        } else {
            posts
        }
    }
}
