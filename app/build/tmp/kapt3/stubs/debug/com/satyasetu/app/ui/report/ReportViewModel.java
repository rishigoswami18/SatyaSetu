package com.satyasetu.app.ui.report;

import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.Report;
import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.ReportRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0006\u0010\u0010\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0015J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0015J\u000e\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0015R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001c"}, d2 = {"Lcom/satyasetu/app/ui/report/ReportViewModel;", "Landroidx/lifecycle/ViewModel;", "reportRepository", "Lcom/satyasetu/app/data/repository/ReportRepository;", "authRepository", "Lcom/satyasetu/app/data/repository/AuthRepository;", "(Lcom/satyasetu/app/data/repository/ReportRepository;Lcom/satyasetu/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/satyasetu/app/ui/report/ReportUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadMyReports", "", "resetForm", "submitReport", "toggleAnonymous", "updateCategory", "c", "", "updateDescription", "d", "updateLocation", "l", "updateTitle", "t", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ReportViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.ReportRepository reportRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.satyasetu.app.ui.report.ReportUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.report.ReportUiState> uiState = null;
    
    @javax.inject.Inject()
    public ReportViewModel(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.ReportRepository reportRepository, @org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.report.ReportUiState> getUiState() {
        return null;
    }
    
    private final void loadMyReports() {
    }
    
    public final void updateTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String t) {
    }
    
    public final void updateDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String d) {
    }
    
    public final void updateCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String c) {
    }
    
    public final void updateLocation(@org.jetbrains.annotations.NotNull()
    java.lang.String l) {
    }
    
    public final void toggleAnonymous() {
    }
    
    public final void submitReport() {
    }
    
    public final void resetForm() {
    }
}