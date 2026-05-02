package com.satyasetu.app.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.Video
import com.satyasetu.app.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FeedUiState(
    val videos: List<Video> = emptyList(),
    val isLoading: Boolean = true,
    val selectedCategory: String? = null,
    val error: String? = null
)

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    private val selectedCategory = MutableStateFlow<String?>(null)

    val uiState: StateFlow<FeedUiState> = selectedCategory
        .flatMapLatest { category ->
            val source = if (category.isNullOrBlank()) {
                videoRepository.observeVideos()
            } else {
                videoRepository.observeVideosByCategory(category)
            }

            source
                .map { videos ->
                    FeedUiState(
                        videos = videos,
                        isLoading = false,
                        selectedCategory = category
                    )
                }
                .onStart {
                    emit(
                        FeedUiState(
                            isLoading = true,
                            selectedCategory = category
                        )
                    )
                }
                .catch { error ->
                    emit(
                        FeedUiState(
                            isLoading = false,
                            selectedCategory = category,
                            error = error.message ?: "Unable to load videos."
                        )
                    )
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FeedUiState()
        )

    fun selectCategory(category: String?) {
        selectedCategory.value = category
    }

    fun likeVideo(videoId: String) {
        viewModelScope.launch {
            videoRepository.likeVideo(videoId)
        }
    }

    fun onVideoViewed(videoId: String) {
        viewModelScope.launch {
            videoRepository.incrementViews(videoId)
        }
    }

    fun seedData() {
        viewModelScope.launch {
            videoRepository.seedSampleVideos()
        }
    }
}
