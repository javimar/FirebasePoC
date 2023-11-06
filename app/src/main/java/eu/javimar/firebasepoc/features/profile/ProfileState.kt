package eu.javimar.firebasepoc.features.profile

import com.google.firebase.auth.FirebaseUser
import eu.javimar.domain.auth.model.TokenInfo

data class ProfileState(
    val user: FirebaseUser? = null,
    val tokenInfo: TokenInfo = TokenInfo(),
    val showExitDialog: Boolean = false
)
