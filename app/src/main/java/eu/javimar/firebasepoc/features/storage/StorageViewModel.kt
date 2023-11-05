package eu.javimar.firebasepoc.features.storage

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
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
import eu.javimar.firebasepoc.core.utils.createFileName
import eu.javimar.firebasepoc.features.storage.state.StorageEvent
import eu.javimar.firebasepoc.features.storage.state.StorageState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val storageManager: StorageManager,
    private val analyticsManager: AnalyticsManager,
): ViewModel() {

    var state by mutableStateOf(StorageState())
        private set

    init {
        analyticsManager.logScreenView(BottomGraphScreens.Storage.route)
        fetchStorage()
    }

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    fun onEvent(event: StorageEvent) {
        when(event) {
            is StorageEvent.AddItem -> launchIntent(event.launcher)
            is StorageEvent.GetUriFile -> readRemoteUri(event.uri)
            is StorageEvent.FileClick -> state = state.copy(fileUri = event.fileUri)
        }
    }

    private fun launchIntent(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            .apply {
                addCategory(Intent.CATEGORY_OPENABLE)
            }
        launcher.launch(intent)
    }

    private fun readRemoteUri(uri: Uri?) {
        uri?.let {
            viewModelScope.launch {
                when(val result = storageManager
                    .uploadFile(fileName = "Entrenos_${createFileName()}", it, "Entrenos")) {

                    is FileResult.Success -> {
                        analyticsManager.buttonClicked("Click: Subida a Firebase Storage")
                        sendUiEvent(UIEvent.ShowSnackbar(
                            message = UIText.StringResource(R.string.storage_upload_ok))
                        )
                    }
                    is FileResult.Error -> {
                        analyticsManager.logError("Error subiendo a storage: ${result.errorMessage}")
                        sendUiEvent(UIEvent.ShowSnackbar(
                            message = UIText.DynamicString(result.errorMessage))
                        )
                    }
                }
            }
        }
    }

    private fun fetchStorage() {
        viewModelScope.launch {
            val files = storageManager.getFilesFromStorage("Images")
            if(files.isEmpty()) {
                sendUiEvent(UIEvent.ShowSnackbar(UIText.StringResource(R.string.storage_no_elements_error)))
            } else {
                state = state.copy(
                    gallery = files
                )
            }
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}