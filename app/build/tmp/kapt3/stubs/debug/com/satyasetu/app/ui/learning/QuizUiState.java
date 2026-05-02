package com.satyasetu.app.ui.learning;

import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.Lesson;
import com.satyasetu.app.data.model.QuizQuestion;
import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.LearningRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001b\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B]\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\b\u00a2\u0006\u0002\u0010\u000fJ\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\bH\u00c6\u0003J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003Ja\u0010\"\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\bH\u00c6\u0001J\u0013\u0010#\u001a\u00020\u000b2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020\bH\u00d6\u0001J\t\u0010&\u001a\u00020\'H\u00d6\u0001R\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0013R\u0011\u0010\r\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0013R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011\u00a8\u0006("}, d2 = {"Lcom/satyasetu/app/ui/learning/QuizUiState;", "", "lesson", "Lcom/satyasetu/app/data/model/Lesson;", "questions", "", "Lcom/satyasetu/app/data/model/QuizQuestion;", "currentIndex", "", "selectedAnswer", "isAnswered", "", "correctCount", "isComplete", "score", "(Lcom/satyasetu/app/data/model/Lesson;Ljava/util/List;IIZIZI)V", "getCorrectCount", "()I", "getCurrentIndex", "()Z", "getLesson", "()Lcom/satyasetu/app/data/model/Lesson;", "getQuestions", "()Ljava/util/List;", "getScore", "getSelectedAnswer", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class QuizUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.satyasetu.app.data.model.Lesson lesson = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.satyasetu.app.data.model.QuizQuestion> questions = null;
    private final int currentIndex = 0;
    private final int selectedAnswer = 0;
    private final boolean isAnswered = false;
    private final int correctCount = 0;
    private final boolean isComplete = false;
    private final int score = 0;
    
    public QuizUiState(@org.jetbrains.annotations.Nullable()
    com.satyasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    java.util.List<com.satyasetu.app.data.model.QuizQuestion> questions, int currentIndex, int selectedAnswer, boolean isAnswered, int correctCount, boolean isComplete, int score) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.satyasetu.app.data.model.Lesson getLesson() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.satyasetu.app.data.model.QuizQuestion> getQuestions() {
        return null;
    }
    
    public final int getCurrentIndex() {
        return 0;
    }
    
    public final int getSelectedAnswer() {
        return 0;
    }
    
    public final boolean isAnswered() {
        return false;
    }
    
    public final int getCorrectCount() {
        return 0;
    }
    
    public final boolean isComplete() {
        return false;
    }
    
    public final int getScore() {
        return 0;
    }
    
    public QuizUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.satyasetu.app.data.model.Lesson component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.satyasetu.app.data.model.QuizQuestion> component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.satyasetu.app.ui.learning.QuizUiState copy(@org.jetbrains.annotations.Nullable()
    com.satyasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    java.util.List<com.satyasetu.app.data.model.QuizQuestion> questions, int currentIndex, int selectedAnswer, boolean isAnswered, int correctCount, boolean isComplete, int score) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}