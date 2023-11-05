package eu.javimar.firebasepoc.core

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}


val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun logd(str: String) {
    Log.d("${"".TAG} - JAVIER "  , str)
}
fun logi(str: String) {
    Log.i("${"".TAG} - JAVIER "  , str)
}
fun logw(str: String) {
    Log.w("${"".TAG} - JAVIER "  , str)
}
fun loge(str: String) {
    Log.e("${"".TAG} - JAVIER "  , str)
}
