package eu.javimar.domain.auth.utils

sealed class FileResult<out T> {
    data class Success<T>(val data: T): FileResult<T>()
    data class Error(val errorMessage: String): FileResult<Nothing>()
}