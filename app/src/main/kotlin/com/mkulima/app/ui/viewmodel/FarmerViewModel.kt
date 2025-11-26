package com.mkulima.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkulima.app.data.FarmerRepository
import com.mkulima.app.data.models.Farmer
import com.mkulima.app.data.models.WeatherAlert
import com.mkulima.app.data.models.PestAlert
import com.mkulima.app.data.models.DailyTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FarmerUiState(
    val farmer: Farmer? = null,
    val weatherAlerts: List<WeatherAlert> = emptyList(),
    val pestAlerts: List<PestAlert> = emptyList(),
    val dailyTasks: List<DailyTask> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class FarmerViewModel(private val repository: FarmerRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(FarmerUiState())
    val uiState: StateFlow<FarmerUiState> = _uiState.asStateFlow()

    fun loadFarmerData(farmerId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val farmer = repository.getFarmer(farmerId)
                val weatherAlerts = farmer?.county?.let { repository.getWeatherAlerts(it) } ?: emptyList()
                val pestAlerts = farmer?.county?.let { repository.getPestAlerts(it) } ?: emptyList()
                val dailyTasks = farmer?.id?.let { repository.getDailyTasks(it) } ?: emptyList()

                _uiState.value = _uiState.value.copy(
                    farmer = farmer,
                    weatherAlerts = weatherAlerts,
                    pestAlerts = pestAlerts,
                    dailyTasks = dailyTasks,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun saveFarmerProfile(
        name: String,
        location: String,
        county: String,
        mainCrop: String,
        farmSize: Double
    ) {
        viewModelScope.launch {
            try {
                val farmer = Farmer(
                    id = "",
                    name = name,
                    location = location,
                    county = county,
                    mainCrop = mainCrop,
                    farmSizeHectares = farmSize,
                    phoneNumber = ""
                )
                repository.saveFarmer(farmer)
                _uiState.value = _uiState.value.copy(farmer = farmer)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to save farmer profile"
                )
            }
        }
    }

    fun updateTaskStatus(taskId: String, completed: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateTaskStatus(taskId, completed)
                val updatedTasks = _uiState.value.dailyTasks.map { task ->
                    if (task.id == taskId) task.copy(completed = completed) else task
                }
                _uiState.value = _uiState.value.copy(dailyTasks = updatedTasks)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to update task"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
