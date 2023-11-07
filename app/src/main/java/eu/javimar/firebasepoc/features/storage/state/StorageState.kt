package eu.javimar.firebasepoc.features.storage.state

import eu.javimar.domain.storage.model.FileStorageInfo
import eu.javimar.firebasepoc.core.utils.UIText

data class StorageState(
    val gallery: List<FileStorageInfo> = emptyList(),
    val emptyGallery: Boolean = false,
    val fileUri: String = "",
    val errorMessage: UIText? = null
)
