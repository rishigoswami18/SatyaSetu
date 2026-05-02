package com.satyasetu.app.ui.myths

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.Myth
import com.satyasetu.app.data.repository.MythRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MythUiState(
    val myths: List<Myth> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class MythViewModel @Inject constructor(
    private val mythRepository: MythRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MythUiState())
    val uiState: StateFlow<MythUiState> = _uiState.asStateFlow()

    init { loadMyths() }

    private fun loadMyths() {
        viewModelScope.launch {
            mythRepository.observeMyths().catch { e ->
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }.collect { myths ->
                _uiState.update { it.copy(myths = myths, isLoading = false) }
            }
        }
    }

    fun likeMythFact(mythId: String) {
        viewModelScope.launch { mythRepository.likeMythFact(mythId) }
    }

    fun seedData() {
        viewModelScope.launch { mythRepository.seedSampleMyths() }
    }
}
