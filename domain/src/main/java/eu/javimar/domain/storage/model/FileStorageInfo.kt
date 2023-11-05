package eu.javimar.domain.storage.model

data class FileStorageInfo(
    val url: String,
    val path: String,
    val name: String,
    val contentType: String,
    val sizeBytes: String,
    val created: String
)
