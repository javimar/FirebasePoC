package eu.javimar.firebasepoc.features.storage.state

import eu.javimar.domain.storage.model.FileStorageInfo

data class StorageState(
    val gallery: List<FileStorageInfo> = emptyList(),
    val fileUri: String = ""
)
