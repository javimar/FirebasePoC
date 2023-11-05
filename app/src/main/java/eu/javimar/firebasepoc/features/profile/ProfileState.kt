package eu.javimar.firebasepoc.features.profile

import com.google.firebase.auth.FirebaseUser

data class ProfileState(
    val user: FirebaseUser? = null,
    val showExitDialog: Boolean = false
)
