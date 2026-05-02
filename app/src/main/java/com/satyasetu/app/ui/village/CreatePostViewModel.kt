package com.satyasetu.app.ui.village

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.Post
import com.satyasetu.app.data.repository.AuthRepository
import com.satyasetu.app.data.repository.PostRepository
import com.satyasetu.app.data.repository.VillageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreatePostUiState(
    val description: String = "",
    val mediaUri: Uri? = null,
    val isVideo: Boolean = false,
    val type: String = Post.TYPE_AWARENESS,
    val isUploading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val villageRepository: VillageRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState: StateFlow<CreatePostUiState> = _uiState.asStateFlow()

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description, error = null) }
    }

    fun updateMedia(uri: Uri?, isVideo: Boolean) {
        _uiState.update { it.copy(mediaUri = uri, isVideo = isVideo, error = null) }
    }

    fun updateType(type: String) {
        _uiState.update { it.copy(type = type, error = null) }
    }

    fun submitPost() {
        val state = _uiState.value
        if (state.isUploading) return

        if (state.description.isBlank() && state.mediaUri == null) {
            _uiState.update {
                it.copy(error = "Add a message or attach media before posting.")
            }
            return
        }

        val user = authRepository.currentUser
        if (user == null) {
            _uiState.update {
                it.copy(error = "Please log in before creating a post.")
            }
            return
        }

        _uiState.update { it.copy(isUploading = true, error = null) }

        viewModelScope.launch {
            val villageId = villageRepository.getCachedVillageId()
                ?: authRepository.getUserProfile(user.uid).getOrNull()?.villageId?.takeIf { it.isNotBlank() }

            if (villageId == null) {
                _uiState.update {
                    it.copy(isUploading = false, error = "No village is assigned to this account yet.")
                }
                return@launch
            }

            val result = postRepository.createPost(
                villageId = villageId,
                userId = user.uid,
                userName = user.displayName?.takeIf { it.isNotBlank() } ?: "Verified User",
                description = state.description.trim(),
                mediaUri = state.mediaUri,
                isVideo = state.isVideo,
                type = state.type
            )

            if (result.isSuccess) {
                _uiState.update { it.copy(isUploading = false, isSuccess = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isUploading = false,
                        error = result.exceptionOrNull()?.message ?: "Unable to create post."
                    )
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = CreatePostUiState()
    }
}
