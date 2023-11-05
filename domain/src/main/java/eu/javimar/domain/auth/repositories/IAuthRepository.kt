package eu.javimar.domain.auth.repositories

interface IAuthRepository {
    suspend fun login()
    suspend fun logout()
    suspend fun createAccount()
    suspend fun getSignedInUser()
}