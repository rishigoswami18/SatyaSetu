package com.satyasetu.app.ui.feed;

import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.Video;
import com.satyasetu.app.data.repository.VideoRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007J\u000e\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007J\u0006\u0010\u0011\u001a\u00020\u000eJ\u0010\u0010\u0012\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0007R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/satyasetu/app/ui/feed/FeedViewModel;", "Landroidx/lifecycle/ViewModel;", "videoRepository", "Lcom/satyasetu/app/data/repository/VideoRepository;", "(Lcom/satyasetu/app/data/repository/VideoRepository;)V", "selectedCategory", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/satyasetu/app/ui/feed/FeedUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "likeVideo", "", "videoId", "onVideoViewed", "seedData", "selectCategory", "category", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class FeedViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.VideoRepository videoRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> selectedCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.feed.FeedUiState> uiState = null;
    
    @javax.inject.Inject()
    public FeedViewModel(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.VideoRepository videoRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.feed.FeedUiState> getUiState() {
        return null;
    }
    
    public final void selectCategory(@org.jetbrains.annotations.Nullable()
    java.lang.String category) {
    }
    
    public final void likeVideo(@org.jetbrains.annotations.NotNull()
    java.lang.String videoId) {
    }
    
    public final void onVideoViewed(@org.jetbrains.annotations.NotNull()
    java.lang.String videoId) {
    }
    
    public final void seedData() {
    }
}