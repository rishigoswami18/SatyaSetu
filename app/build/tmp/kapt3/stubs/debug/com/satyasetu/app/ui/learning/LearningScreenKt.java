package com.satyasetu.app.ui.learning;

import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.satyasetu.app.data.model.Lesson;
import com.satyasetu.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a&\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u00a8\u0006\u000e"}, d2 = {"LearningScreen", "", "onNavigateToQuiz", "Lkotlin/Function1;", "", "viewModel", "Lcom/satyasetu/app/ui/learning/LearningViewModel;", "LessonCard", "lesson", "Lcom/satyasetu/app/data/model/Lesson;", "isCompleted", "", "onStartQuiz", "Lkotlin/Function0;", "app_debug"})
public final class LearningScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void LearningScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToQuiz, @org.jetbrains.annotations.NotNull()
    com.satyasetu.app.ui.learning.LearningViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LessonCard(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.model.Lesson lesson, boolean isCompleted, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartQuiz) {
    }
}