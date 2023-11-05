package eu.javimar.firebasepoc.features.storage.state

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult

sealed interface StorageEvent {
    data class GetUriFile(val uri: Uri?) : StorageEvent
    data class AddItem(val launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) : StorageEvent
    data class FileClick(val fileUri: String): StorageEvent
}