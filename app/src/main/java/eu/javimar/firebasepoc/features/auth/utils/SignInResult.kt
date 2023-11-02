package eu.javimar.firebasepoc.features.auth.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
): Parcelable

@Parcelize
@Serializable
data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
): Parcelable
