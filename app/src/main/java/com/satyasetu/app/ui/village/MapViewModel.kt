package com.satyasetu.app.ui.village

import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyasetu.app.data.model.Village
import com.satyasetu.app.data.repository.AuthRepository
import com.satyasetu.app.data.repository.VillageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

data class MapUiState(
    val isLoading: Boolean = false,
    val selectedLat: Double = 20.5937, // Default India
    val selectedLng: Double = 78.9629,
    val addressText: String = "स्थान चुनें",
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val villageRepository: VillageRepository,
    private val authRepository: AuthRepository,
    private val geocoder: Geocoder
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun updateCameraPosition(lat: Double, lng: Double) {
        _uiState.update { it.copy(selectedLat = lat, selectedLng = lng) }
        // Fetch full detailed address for the position
        viewModelScope.launch {
            val address = getAddressFromLocation(lat, lng)
            val fullAddress = address?.getAddressLine(0) ?: 
                             (address?.subLocality ?: address?.locality ?: "अज्ञात स्थान")
            _uiState.update { it.copy(addressText = fullAddress) }
        }
    }

    fun confirmLocation() {
        val user = authRepository.currentUser ?: return
        val stateSnapshot = _uiState.value
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                // Reverse Geocode
                val address = getAddressFromLocation(stateSnapshot.selectedLat, stateSnapshot.selectedLng)
                
                if (address == null) {
                    _uiState.update { it.copy(isLoading = false, error = "स्थान का पता नहीं चला। कृपया दोबारा कोशिश करें।") }
                    return@launch
                }

                // Improved rural address extraction
                val villageName = address.locality ?: address.subLocality ?: address.subAdminArea ?: run {
                    // Fallback: extract from address line if others are null
                    val line = address.getAddressLine(0) ?: ""
                    val parts = line.split(",")
                    if (parts.isNotEmpty()) parts[0].trim() else "अज्ञात गाँव"
                }
                val district = address.subAdminArea ?: address.adminArea ?: "अज्ञात ज़िला"
                val stateName = address.adminArea ?: "अज्ञात राज्य"
                
                // Full address for display
                val displayAddress = address.getAddressLine(0) ?: "$villageName, $district"

                // Assign Village
                val result = villageRepository.assignVillageToUser(
                    userId = user.uid,
                    villageName = villageName,
                    lat = stateSnapshot.selectedLat,
                    lng = stateSnapshot.selectedLng,
                    district = district,
                    state = stateName
                )

                if (result.isSuccess) {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    @Suppress("DEPRECATION")
    private suspend fun getAddressFromLocation(lat: Double, lng: Double): Address? = withContext(Dispatchers.IO) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // For newer API levels, Geocoder has a callback, but for simplicity in coroutines,
                // we'll use the synchronous one or wrap it. The older one is still available but deprecated.
                // We'll just use the old one for compatibility and simplicity in this example.
                geocoder.getFromLocation(lat, lng, 1)?.firstOrNull()
            } else {
                geocoder.getFromLocation(lat, lng, 1)?.firstOrNull()
            }
        } catch (e: Exception) {
            null
        }
    }
}
