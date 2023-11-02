package eu.javimar.firebasepoc.features.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class StorageManager(
    private val context: Context,
    path: String,
) {

    private val storageRef = FirebaseStorage.getInstance().reference.child(path)

    suspend fun getFile(): Bitmap? {

        var bitmap: Bitmap? = null

        val localFile = withContext(Dispatchers.IO) {
            File.createTempFile("tempImage", "jpg")
        }

        storageRef.getFile(localFile)
            .addOnSuccessListener {
                bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            }
            .addOnFailureListener {
                it.printStackTrace()
                Toast.makeText(
                    context,
                    "Failed to retrive doc $it",
                    Toast.LENGTH_LONG
                ).show()
            }.await()
        return bitmap
    }
}