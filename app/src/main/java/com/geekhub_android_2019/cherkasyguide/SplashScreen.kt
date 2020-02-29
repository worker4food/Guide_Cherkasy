package com.geekhub_android_2019.cherkasyguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            FirebaseAuth.getInstance().signInAnonymously().await()

            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }
    }
}
