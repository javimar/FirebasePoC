package eu.javimar.firebasepoc.features.storage.otherfiles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.coachpoc.R
import eu.javimar.domain.auth.utils.FileResult
import eu.javimar.firebasepoc.core.firebase.AnalyticsManager
import eu.javimar.firebasepoc.core.firebase.StorageManager
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import eu.javimar.firebasepoc.core.utils.UIText
import eu.javimar.firebasepoc.features.storage.state.StorageEvent
import eu.javimar.firebasepoc.features.storage.state.StorageState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherFilesViewModel @Inject constructor(
    private val storageManager: StorageManager,
    private val analyticsManager: AnalyticsManager,
): ViewModel() {

    var state by mutableStateOf(StorageState())
        private set

    init {
        analyticsManager.logScreenView(BottomGraphScreens.OtherFiles.route)
        fetchStorage()
    }

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    fun onEvent(event: StorageEvent) {
        when(event) {
            is StorageEvent.FileClick -> state = state.copy(fileUri = event.fileUri)
            else -> Unit
        }
    }

    private fun fetchStorage() {
        viewModelScope.launch {
            when(val result = storageManager.getFilesFromStorage("Images")) {
                is FileResult.Success -> {
                    if(result.data.isEmpty()) {
                        sendUiEvent(UIEvent.ShowSnackbar(UIText.StringResource(R.string.storage_no_elements_error)))
                    } else {
                        state = state.copy(
                            gallery = result.data
                        )
                    }
                }
                is FileResult.Error -> {
                    analyticsManager.logError("Error obtiendo archivos desde storage: ${result.errorMessage}")
                    sendUiEvent(UIEvent.ShowSnackbar(
                        message = UIText.DynamicString(result.errorMessage))
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}