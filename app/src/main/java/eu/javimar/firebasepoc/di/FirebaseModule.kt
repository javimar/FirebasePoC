package eu.javimar.firebasepoc.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.javimar.firebasepoc.core.firebase.AnalyticsManager
import eu.javimar.firebasepoc.core.firebase.GoogleAuthManager
import eu.javimar.firebasepoc.core.firebase.StorageManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(@ApplicationContext context: Context): GoogleAuthManager = GoogleAuthManager(context)

    @Provides
    @Singleton
    fun provideAnalyticsManager(@ApplicationContext context: Context, ): AnalyticsManager = AnalyticsManager(context)

    @Provides
    @Singleton
    fun provideStorageManager(@ApplicationContext context: Context, auth: GoogleAuthManager): StorageManager =
        StorageManager(context, auth.getSignedInUser()!!.uid)
}