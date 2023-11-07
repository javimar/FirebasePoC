package eu.javimar.firebasepoc.core

import android.util.Log
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.core.utils.FileResult.Companion.ErrorCode
import eu.javimar.firebasepoc.core.utils.UIText

fun ErrorCode.issueStorageNetworkError(): UIText {
    return when(this) {
        ErrorCode.PERMISSION_DENIED -> {
            UIText.StringResource(R.string.storage_no_permission_error)
        }
        ErrorCode.UNKNOWN_ERROR -> {
            UIText.StringResource(R.string.unknown_error)
        }
    }
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
