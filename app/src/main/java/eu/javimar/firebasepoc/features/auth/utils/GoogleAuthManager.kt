package eu.javimar.firebasepoc.features.auth.utils

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import eu.javimar.coachpoc.BuildConfig
import eu.javimar.domain.auth.utils.AuthRes
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthManager(
    private val context: Context
) {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val signInClient = Identity.getSignInClient(context)

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.WEB_CLIENT_ID)
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    suspend fun signInAnonymously(): AuthRes<FirebaseUser> {
        return try {
            val result = auth.signInAnonymously().await()
            AuthRes.Success(result.user ?: throw Exception("Error while siging in anonymously"))
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error while singing in anonymously")
        }
    }

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthRes<FirebaseUser?> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            AuthRes.Success(authResult.user)
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error while creating user")
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser?> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            AuthRes.Success(authResult.user)
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error while signing in")
        }
    }

    suspend fun resetPassword(email: String): AuthRes<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthRes.Success(Unit)
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error white resetting password")
        }
    }

    suspend fun signIn(): IntentSender? {
        val result = try {
            signInClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }
        catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error al iniciar sesión")
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun getSignWithIntent(intent: Intent): AuthRes<FirebaseUser> {
        val credential = signInClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            AuthRes.Success(user ?: throw Exception("Error signing in Google"))
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            AuthRes.Error(e.message ?: "Error signing in Google")
        }
    }

    fun handleSignInResult(task: Task<GoogleSignInAccount>): AuthRes<GoogleSignInAccount>? {
        return try {
            val account = task.getResult(ApiException::class.java)
            AuthRes.Success(account)
        } catch (e: ApiException) {
            AuthRes.Error(e.message ?: "Google sign-in failed.")
        }
    }

    suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthRes<FirebaseUser>? {
        return try {
            val firebaseUser = auth.signInWithCredential(credential).await()
            firebaseUser.user?.let {
                AuthRes.Success(it)
            } ?: throw Exception("Sign in with Google failed.")
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Sign in with Google failed.")
        }
    }

    fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
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
            signInClient.signOut().await()
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): FirebaseUser? = auth.currentUser
}