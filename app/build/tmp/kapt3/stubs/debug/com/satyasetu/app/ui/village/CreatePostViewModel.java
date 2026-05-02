package com.satyasetu.app.ui.village;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.Post;
import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.PostRepository;
import com.satyasetu.app.data.repository.VillageRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011J\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015J\u0018\u0010\u0016\u001a\u00020\u00112\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u0015R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/satyasetu/app/ui/village/CreatePostViewModel;", "Landroidx/lifecycle/ViewModel;", "postRepository", "Lcom/satyasetu/app/data/repository/PostRepository;", "villageRepository", "Lcom/satyasetu/app/data/repository/VillageRepository;", "authRepository", "Lcom/satyasetu/app/data/repository/AuthRepository;", "(Lcom/satyasetu/app/data/repository/PostRepository;Lcom/satyasetu/app/data/repository/VillageRepository;Lcom/satyasetu/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/satyasetu/app/ui/village/CreatePostUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "resetState", "", "submitPost", "updateDescription", "description", "", "updateMedia", "uri", "Landroid/net/Uri;", "isVideo", "", "updateType", "type", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CreatePostViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.PostRepository postRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.VillageRepository villageRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.satyasetu.app.ui.village.CreatePostUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.village.CreatePostUiState> uiState = null;
    
    @javax.inject.Inject()
    public CreatePostViewModel(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.PostRepository postRepository, @org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.VillageRepository villageRepository, @org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.village.CreatePostUiState> getUiState() {
        return null;
    }
    
    public final void updateDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void updateMedia(@org.jetbrains.annotations.Nullable()
    android.net.Uri uri, boolean isVideo) {
    }
    
    public final void updateType(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
    }
    
    public final void submitPost() {
    }
    
    public final void resetState() {
    }
}