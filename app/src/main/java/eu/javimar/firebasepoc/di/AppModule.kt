package eu.javimar.firebasepoc.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.javimar.domain.auth.utils.IFormValidator
import eu.javimar.firebasepoc.features.auth.utils.FormValidator
import eu.javimar.firebasepoc.features.auth.utils.GoogleAuthManager
import eu.javimar.firebasepoc.features.storage.utils.StorageManager
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(@ApplicationContext context: Context): GoogleAuthManager {
        return GoogleAuthManager(context)
    }

    @Provides
    @Singleton
    fun provideStorageManager(
        @ApplicationContext context: Context,
        auth: GoogleAuthManager
    ): StorageManager {
        return StorageManager(context, auth.getSignedInUser()!!.uid)
    }

    @Provides
    @Singleton
    @Named("hasInternet")
    fun provideHasInternet(@ApplicationContext context: Context): Boolean {
        var hasInternet = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                hasInternet = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return hasInternet
    }

    @Provides
    @Singleton
    fun providePlatfoPasswordChecker(): IFormValidator = FormValidator()
}