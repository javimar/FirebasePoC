package eu.javimar.firebasepoc.features.auth.utils

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import eu.javimar.coachpoc.BuildConfig
import eu.javimar.domain.auth.utils.AuthRes
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthManager(
    context: Context
) {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    private val googleSignInClient = Identity.getSignInClient(context)

    suspend fun signInAnonymously(): AuthRes<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInAnonymously().await()
            AuthRes.Success(result.user ?: throw Exception("Error while login in anonymously"))
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error while login in anonymously")
        }
    }

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthRes<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthRes.Success(authResult.user)
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error while creating user")
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthRes.Success(authResult.user)
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error while signing in")
        }
    }

    suspend fun resetPassword(email: String): AuthRes<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            AuthRes.Success(Unit)
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error white resetting password")
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    suspend fun signOut() {
        try {
            googleSignInClient.signOut().await()
            firebaseAuth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): FirebaseUser? = firebaseAuth.currentUser

    suspend fun getGoogleSignWithIntent(intent: Intent): AuthRes<FirebaseUser> {
        val credential = googleSignInClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = firebaseAuth.signInWithCredential(googleCredential).await().user
            AuthRes.Success(user ?: throw Exception("Login Google error"))
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            AuthRes.Error(e.message ?: "Login Google error")
        }
    }

    suspend fun signIn(): IntentSender? {
        val result = try {
            googleSignInClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }
        catch(e: Exception) {
            AuthRes.Error(e.message ?: "Sign in Error")
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }
}