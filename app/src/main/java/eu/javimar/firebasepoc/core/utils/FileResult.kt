package eu.javimar.firebasepoc.core.utils

import org.json.JSONException
import org.json.JSONObject

sealed class FileResult<out T> {
    data class Success<T>(val data: T) : FileResult<T>()
    data class Error(val error: ErrorResponse) : FileResult<Nothing>()
    data class ErrorResponse(
        val detailedMessage: String = "Unknown Error",
        val errorCode: ErrorCode = ErrorCode.UNKNOWN_ERROR,
        val message: String = "Unknown Error"
    )

    companion object {
        fun <T> processError(e: Exception): FileResult<T> = Error(parseError(e))
        private fun parseError(e: Exception): ErrorResponse {
            e.cause?.let {
                try {
                    //val errorJson = JSONObject(it.cause?.message.toString()).getJSONObject("error") works for upload errors :-(
                    val errorJson = JSONObject(it.message.toString()).getJSONObject("error")
                    val errorCode = getErrorCode(errorJson.getInt("code"))
                    val errorMessage = errorJson.getString("message")
                    return ErrorResponse(e.message.toString(), errorCode, errorMessage)
                } catch (e: JSONException) {
                    return ErrorResponse("Error parsing response")
                }
            }
            return ErrorResponse()
        }

        private fun getErrorCode(error: Int): ErrorCode {
            return when(error) {
                in 400..405 -> ErrorCode.PERMISSION_DENIED
                else -> ErrorCode.UNKNOWN_ERROR
            }
        }

        enum class ErrorCode {
            PERMISSION_DENIED,
            UNKNOWN_ERROR
        }
    }
}
