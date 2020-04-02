package com.geekhub_android_2019.cherkasyguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInAndProcess()
    }

    private fun signInAndProcess() = lifecycleScope.launch {
        try {
            FirebaseAuth.getInstance().signInAnonymously().await()

            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        } catch(e: FirebaseNetworkException) {
            showError()
        }
    }

    private fun showError() {
        val vw = findViewById<View>(android.R.id.content)

        Snackbar.make(vw, R.string.error_no_network, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.action_try_again) { signInAndProcess() }
            .show()
    }
}
