package com.satyasetu.app.ui.tracker;

import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.DevelopmentItem;
import com.satyasetu.app.data.repository.TrackerRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\u0006\u0010\u000e\u001a\u00020\rJ\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0012"}, d2 = {"Lcom/satyasetu/app/ui/tracker/TrackerViewModel;", "Landroidx/lifecycle/ViewModel;", "trackerRepository", "Lcom/satyasetu/app/data/repository/TrackerRepository;", "(Lcom/satyasetu/app/data/repository/TrackerRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/satyasetu/app/ui/tracker/TrackerUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadItems", "", "seedData", "updateSearch", "query", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TrackerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.TrackerRepository trackerRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.satyasetu.app.ui.tracker.TrackerUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.tracker.TrackerUiState> uiState = null;
    
    @javax.inject.Inject()
    public TrackerViewModel(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.TrackerRepository trackerRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.tracker.TrackerUiState> getUiState() {
        return null;
    }
    
    private final void loadItems() {
    }
    
    public final void updateSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void seedData() {
    }
}