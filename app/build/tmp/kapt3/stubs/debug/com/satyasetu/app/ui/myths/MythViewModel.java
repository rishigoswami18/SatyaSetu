package com.satyasetu.app.ui.myths;

import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.Myth;
import com.satyasetu.app.data.repository.MythRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u00020\rH\u0002J\u0006\u0010\u0011\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0012"}, d2 = {"Lcom/satyasetu/app/ui/myths/MythViewModel;", "Landroidx/lifecycle/ViewModel;", "mythRepository", "Lcom/satyasetu/app/data/repository/MythRepository;", "(Lcom/satyasetu/app/data/repository/MythRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/satyasetu/app/ui/myths/MythUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "likeMythFact", "", "mythId", "", "loadMyths", "seedData", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MythViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.MythRepository mythRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.satyasetu.app.ui.myths.MythUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.myths.MythUiState> uiState = null;
    
    @javax.inject.Inject()
    public MythViewModel(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.MythRepository mythRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.myths.MythUiState> getUiState() {
        return null;
    }
    
    private final void loadMyths() {
    }
    
    public final void likeMythFact(@org.jetbrains.annotations.NotNull()
    java.lang.String mythId) {
    }
    
    public final void seedData() {
    }
}