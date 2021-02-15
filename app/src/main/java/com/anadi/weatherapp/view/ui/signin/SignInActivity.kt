package com.anadi.weatherapp.view.ui.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherapp.R
import com.anadi.weatherapp.databinding.*
import com.anadi.weatherapp.domain.user.UserRepository
import com.anadi.weatherapp.view.ui.BaseActivity
import com.anadi.weatherapp.view.ui.mainactivity.MainActivity
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

private const val RC_SIGN_IN = 9001

class SignInActivity : BaseActivity(R.layout.sign_in_activity) {

    private val binding: SignInActivityBinding by viewBinding()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var signInClient: GoogleSignInClient

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.signInButton.setOnClickListener { trySignIn() }
    }

    private fun trySignIn() {
        val signInIntent: Intent = signInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)

                lifecycleScope.launch { firebaseAuthWithGoogle(account) }
            } catch (e: ApiException) {
                Timber.w(e, "Google sign in failed")
            }
        }
    }

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount) = withContext(Dispatchers.IO) {
        Timber.d("firebaseAuthWithGoogle: ${account.id}")
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(this@SignInActivity) { lifecycleScope.launch { authSuccess(it) } }
                .addOnFailureListener(this@SignInActivity) { authFailed(it) }
    }

    private suspend fun authSuccess(authResult: AuthResult) {
        Timber.d("signInWithCredential:success")

        userRepository.activate(authResult.user!!, authResult.additionalUserInfo?.username)

        startActivity(MainActivity.getIntent(this))
        finish()
    }

    private fun authFailed(e: Exception) {
        Timber.w(e, "signInWithCredential")
        Toasty.error(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
    }
}
