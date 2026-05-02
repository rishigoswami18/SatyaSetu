package com.satyasetu.app.ui.learning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.Lesson
import com.satyasetu.app.data.model.QuizQuestion
import com.satyasetu.app.data.repository.AuthRepository
import com.satyasetu.app.data.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LearningUiState(
    val lessons: List<Lesson> = emptyList(),
    val isLoading: Boolean = true,
    val selectedCategory: String? = null,
    val completedLessons: Set<String> = emptySet(),
    val error: String? = null
)

data class QuizUiState(
    val lesson: Lesson? = null,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: Int = -1,
    val isAnswered: Boolean = false,
    val correctCount: Int = 0,
    val isComplete: Boolean = false,
    val score: Int = 0
)

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val learningRepository: LearningRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearningUiState())
    val uiState: StateFlow<LearningUiState> = _uiState.asStateFlow()

    private val _quizState = MutableStateFlow(QuizUiState())
    val quizState: StateFlow<QuizUiState> = _quizState.asStateFlow()

    init { loadLessons() }

    private fun loadLessons() {
        viewModelScope.launch {
            learningRepository.observeLessons().catch { e ->
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }.collect { lessons ->
                _uiState.update { it.copy(lessons = lessons, isLoading = false) }
            }
        }
    }

    fun selectCategory(category: String?) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun loadQuiz(lessonId: String) {
        val lesson = _uiState.value.lessons.find { it.lessonId == lessonId } ?: return
        _quizState.update {
            QuizUiState(
                lesson = lesson,
                questions = lesson.quiz,
                currentIndex = 0,
                selectedAnswer = -1,
                isAnswered = false,
                correctCount = 0,
                isComplete = false
            )
        }
    }

    fun selectAnswer(index: Int) {
        if (_quizState.value.isAnswered) return
        val state = _quizState.value
        val isCorrect = index == state.questions[state.currentIndex].correctOptionIndex
        _quizState.update {
            it.copy(
                selectedAnswer = index,
                isAnswered = true,
                correctCount = it.correctCount + if (isCorrect) 1 else 0
            )
        }
    }

    fun nextQuestion() {
        val state = _quizState.value
        if (state.currentIndex + 1 >= state.questions.size) {
            val score = (state.correctCount * 100) / state.questions.size
            _quizState.update { it.copy(isComplete = true, score = score) }
            // Save score
            val userId = authRepository.currentUser?.uid ?: return
            val lessonId = state.lesson?.lessonId ?: return
            viewModelScope.launch {
                learningRepository.saveQuizScore(userId, lessonId, score)
                learningRepository.markLessonComplete(userId, lessonId)
            }
        } else {
            _quizState.update {
                it.copy(
                    currentIndex = it.currentIndex + 1,
                    selectedAnswer = -1,
                    isAnswered = false
                )
            }
        }
    }

    fun seedData() {
        viewModelScope.launch { learningRepository.seedSampleLessons() }
    }
}
