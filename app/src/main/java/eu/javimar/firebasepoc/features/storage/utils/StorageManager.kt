package eu.javimar.firebasepoc.features.storage.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class StorageManager(
    private val context: Context,
    private val uid: String
) {

    private val storage = Firebase.storage
    private val storageRef = storage.reference
    //private val authManager = AuthManager(context)

    fun getStorageReference(): StorageReference {
        return storageRef.child("Images").child(uid)
    }

    suspend fun uploadFile(fileName: String, filePath: Uri) {
        val fileRef = getStorageReference().child(fileName)
        val uploadTask = fileRef.putFile(filePath)
        uploadTask.await()
    }

    suspend fun getUserImages(): List<String> {
        val imageUrls = mutableListOf<String>()
        val listResults: ListResult = getStorageReference().listAll().await()
        for(item in listResults.items) {
            val url = item.downloadUrl.await().toString()
            imageUrls.add(url)
        }
        return imageUrls
    }


    suspend fun getFile(path: String): Bitmap? {

        return try {
            val storageRef = FirebaseStorage.getInstance().reference.child(path)
            var bitmap: Bitmap? = null

            val localFile = withContext(Dispatchers.IO) {
                File.createTempFile("baby", "jpg")
            }
            storageRef.getFile(localFile)
                .addOnSuccessListener {
                    bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    Toast.makeText(
                        context,
                        "Failed to retrive $it",
                        Toast.LENGTH_LONG
                    ).show()
                }.await()
            bitmap
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
    }
}