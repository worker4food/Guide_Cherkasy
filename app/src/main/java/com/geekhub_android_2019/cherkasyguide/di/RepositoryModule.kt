package com.geekhub_android_2019.cherkasyguide.di

import com.geekhub_android_2019.cherkasyguide.BuildConfig
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import org.koin.dsl.module

val repositoryModule = module {
    single { Firebase.app }
    single { Firebase.firestore(get()).document("/") }
    single { FirebaseAuth.getInstance(get()) }
    single { Repository(get(), get()) }
}
