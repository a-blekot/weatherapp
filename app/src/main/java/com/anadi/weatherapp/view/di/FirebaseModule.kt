package com.anadi.weatherapp.view.di

import android.content.Context
import com.anadi.weatherapp.R
import com.anadi.weatherapp.data.db.AppDatabase
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.*
import javax.inject.*

@Module
@Suppress("UtilityClassWithPublicConstructor")
class FirebaseModule {

    companion object {

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        @Singleton
        fun provideGoogleSignInClient(context: Context): GoogleSignInClient {
            val signInOptions = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            return GoogleSignIn.getClient(context, signInOptions)
        }


        @Provides
        @Singleton
        @Named("Global")
        fun provideDatabaseReference(): DatabaseReference {
            return FirebaseDatabase.getInstance().reference
        }

        @Provides
        @Singleton
        @Named("Users")
        fun provideUserDatabaseReference(@Named("Global") root: DatabaseReference) =
                root.child("users")

        @Provides
        @Singleton
        @Named("Locations")
        fun provideLocationsDatabaseReference(@Named("Global") root: DatabaseReference) =
                root.child("locations")

        @Provides
        @Singleton
        @Named("Messages")
        fun provideMessagesDatabaseReference(@Named("Global") root: DatabaseReference) =
                root.child("messages")
    }
}
