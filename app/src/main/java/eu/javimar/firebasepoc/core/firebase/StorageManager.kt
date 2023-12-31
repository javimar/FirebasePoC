package eu.javimar.firebasepoc.core.firebase

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import eu.javimar.domain.storage.model.FileStorageInfo
import eu.javimar.firebasepoc.core.utils.FileResult
import eu.javimar.firebasepoc.core.utils.convertMillisToDate
import kotlinx.coroutines.tasks.await

class StorageManager {
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    private fun getStorageReference(remoteFolder: String): StorageReference {
        return storageRef.child(remoteFolder)
    }

    suspend fun uploadFile(fileName: String, filePath: Uri, remoteFolder: String): FileResult<Unit> {
        return try {
            val fileRef = getStorageReference(remoteFolder).child(fileName)
            val uploadTask = fileRef.putFile(filePath)
            uploadTask.await()
            FileResult.Success(Unit)
        } catch(e: Exception) {
            e.printStackTrace()
            FileResult.processError(e)
        }
    }

    suspend fun getFilesFromStorage(remoteFolder: String): FileResult<List<FileStorageInfo>> {
        return try {
            val fileInfo = mutableListOf<FileStorageInfo>()
            val listResults: ListResult = getStorageReference(remoteFolder).listAll().await()
            for(item in listResults.items) {
                val meta = item.metadata.await()
                fileInfo.add(
                    FileStorageInfo(
                        url = item.downloadUrl.await().toString(),
                        path = item.path,
                        name = item.name,
                        contentType = meta.contentType.toString(),
                        sizeBytes = meta.sizeBytes.toString(),
                        created = convertMillisToDate(meta.creationTimeMillis)
                    )
                )
            }
            FileResult.Success(fileInfo)
        } catch(e: Exception) {
            e.printStackTrace()
            FileResult.processError(e)
        }
    }
}