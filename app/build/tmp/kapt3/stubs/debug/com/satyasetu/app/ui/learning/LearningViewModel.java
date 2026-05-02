package com.satyasetu.app.ui.learning;

import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.Lesson;
import com.satyasetu.app.data.model.QuizQuestion;
import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.LearningRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u000e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0013J\u0006\u0010\u0018\u001a\u00020\u0013J\u000e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u00020\u00132\b\u0010\u001d\u001a\u0004\u0018\u00010\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000f\u00a8\u0006\u001e"}, d2 = {"Lcom/satyasetu/app/ui/learning/LearningViewModel;", "Landroidx/lifecycle/ViewModel;", "learningRepository", "Lcom/satyasetu/app/data/repository/LearningRepository;", "authRepository", "Lcom/satyasetu/app/data/repository/AuthRepository;", "(Lcom/satyasetu/app/data/repository/LearningRepository;Lcom/satyasetu/app/data/repository/AuthRepository;)V", "_quizState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/satyasetu/app/ui/learning/QuizUiState;", "_uiState", "Lcom/satyasetu/app/ui/learning/LearningUiState;", "quizState", "Lkotlinx/coroutines/flow/StateFlow;", "getQuizState", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "loadLessons", "", "loadQuiz", "lessonId", "", "nextQuestion", "seedData", "selectAnswer", "index", "", "selectCategory", "category", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class LearningViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.LearningRepository learningRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.satyasetu.app.ui.learning.LearningUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.learning.LearningUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.satyasetu.app.ui.learning.QuizUiState> _quizState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.learning.QuizUiState> quizState = null;
    
    @javax.inject.Inject()
    public LearningViewModel(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.LearningRepository learningRepository, @org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.learning.LearningUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.learning.QuizUiState> getQuizState() {
        return null;
    }
    
    private final void loadLessons() {
    }
    
    public final void selectCategory(@org.jetbrains.annotations.Nullable()
    java.lang.String category) {
    }
    
    public final void loadQuiz(@org.jetbrains.annotations.NotNull()
    java.lang.String lessonId) {
    }
    
    public final void selectAnswer(int index) {
    }
    
    public final void nextQuestion() {
    }
    
    public final void seedData() {
    }
}