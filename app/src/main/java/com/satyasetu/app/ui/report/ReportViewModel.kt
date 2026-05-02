package com.satyasetu.app.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.Report
import com.satyasetu.app.data.repository.AuthRepository
import com.satyasetu.app.data.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportUiState(
    val title: String = "",
    val description: String = "",
    val category: String = Report.CATEGORY_FAKE_BABA,
    val location: String = "",
    val isAnonymous: Boolean = false,
    val isLoading: Boolean = false,
    val isSubmitted: Boolean = false,
    val error: String? = null,
    val myReports: List<Report> = emptyList()
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init { loadMyReports() }

    private fun loadMyReports() {
        val userId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            reportRepository.observeUserReports(userId).collect { reports ->
                _uiState.update { it.copy(myReports = reports) }
            }
        }
    }

    fun updateTitle(t: String) { _uiState.update { it.copy(title = t, error = null) } }
    fun updateDescription(d: String) { _uiState.update { it.copy(description = d) } }
    fun updateCategory(c: String) { _uiState.update { it.copy(category = c) } }
    fun updateLocation(l: String) { _uiState.update { it.copy(location = l) } }
    fun toggleAnonymous() { _uiState.update { it.copy(isAnonymous = !it.isAnonymous) } }

    fun submitReport() {
        val state = _uiState.value
        if (state.title.isBlank() || state.description.isBlank()) {
            _uiState.update { it.copy(error = "कृपया शीर्षक और विवरण भरें") }
            return
        }
        val user = authRepository.currentUser ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val report = Report(
                userId = user.uid,
                userName = if (state.isAnonymous) "गुमनाम" else (user.displayName ?: "User"),
                title = state.title, description = state.description,
                category = state.category, location = state.location,
                isAnonymous = state.isAnonymous
            )
            reportRepository.submitReport(report)
                .onSuccess { _uiState.update { it.copy(isLoading = false, isSubmitted = true) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun resetForm() { _uiState.update { ReportUiState(myReports = it.myReports) } }
}
