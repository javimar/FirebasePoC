package eu.javimar.firebasepoc.core

import android.util.Log

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
