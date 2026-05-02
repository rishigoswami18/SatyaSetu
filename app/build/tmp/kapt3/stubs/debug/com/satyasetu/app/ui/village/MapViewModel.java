package com.satyasetu.app.ui.village;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import androidx.lifecycle.ViewModel;
import com.satyasetu.app.data.model.Village;
import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.VillageRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0010\u001a\u00020\u0011J \u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/satyasetu/app/ui/village/MapViewModel;", "Landroidx/lifecycle/ViewModel;", "villageRepository", "Lcom/satyasetu/app/data/repository/VillageRepository;", "authRepository", "Lcom/satyasetu/app/data/repository/AuthRepository;", "geocoder", "Landroid/location/Geocoder;", "(Lcom/satyasetu/app/data/repository/VillageRepository;Lcom/satyasetu/app/data/repository/AuthRepository;Landroid/location/Geocoder;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/satyasetu/app/ui/village/MapUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "confirmLocation", "", "getAddressFromLocation", "Landroid/location/Address;", "lat", "", "lng", "(DDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateCameraPosition", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MapViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.VillageRepository villageRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.satyasetu.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final android.location.Geocoder geocoder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.satyasetu.app.ui.village.MapUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.village.MapUiState> uiState = null;
    
    @javax.inject.Inject()
    public MapViewModel(@org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.VillageRepository villageRepository, @org.jetbrains.annotations.NotNull()
    com.satyasetu.app.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    android.location.Geocoder geocoder) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.satyasetu.app.ui.village.MapUiState> getUiState() {
        return null;
    }
    
    public final void updateCameraPosition(double lat, double lng) {
    }
    
    public final void confirmLocation() {
    }
    
    @kotlin.Suppress(names = {"DEPRECATION"})
    private final java.lang.Object getAddressFromLocation(double lat, double lng, kotlin.coroutines.Continuation<? super android.location.Address> $completion) {
        return null;
    }
}