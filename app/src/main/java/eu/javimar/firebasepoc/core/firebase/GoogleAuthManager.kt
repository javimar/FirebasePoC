package eu.javimar.firebasepoc.core.firebase

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
import eu.javimar.domain.auth.model.TokenInfo
import eu.javimar.firebasepoc.core.utils.FileResult
import eu.javimar.firebasepoc.core.utils.JWTUtils
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthManager(
    context: Context
) {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    private val googleSignInClient = Identity.getSignInClient(context)

    suspend fun signInAnonymously(): FileResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInAnonymously().await()
            FileResult.Success(result.user ?: throw Exception("Error while login in anonymously"))
        } catch(e: Exception) {
            FileResult.processError(e)
        }
    }

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): FileResult<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            FileResult.Success(authResult.user)
        } catch(e: Exception) {
            FileResult.processError(e)
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): FileResult<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            FileResult.Success(authResult.user)
        } catch(e: Exception) {
            FileResult.processError(e)
        }
    }

    suspend fun resetPassword(email: String): FileResult<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            FileResult.Success(Unit)
        } catch(e: Exception) {
            FileResult.processError(e)
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

    suspend fun getTokenInfo(): TokenInfo {
        val token = firebaseAuth.currentUser?.getIdToken(false)?.await()?.token
        return JWTUtils(token).extractInformation()
    }

    suspend fun getGoogleSignWithIntent(intent: Intent): FileResult<FirebaseUser> {
        val credential = googleSignInClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = firebaseAuth.signInWithCredential(googleCredential).await().user
            FileResult.Success(user ?: throw Exception("Login Google error"))
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            FileResult.processError(e)
        }
    }

    suspend fun signIn(): IntentSender? {
        val result = try {
            googleSignInClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }
        catch(e: Exception) {
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }
}