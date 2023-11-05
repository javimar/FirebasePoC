package eu.javimar.domain.auth.utils

sealed class AuthRes<out T> {
    data class Success<T>(val data: T): AuthRes<T>()
    data class Error(val errorMessage: String): AuthRes<Nothing>()
}