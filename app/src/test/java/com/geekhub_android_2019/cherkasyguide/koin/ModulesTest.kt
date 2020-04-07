package com.geekhub_android_2019.cherkasyguide.koin

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.geekhub_android_2019.cherkasyguide.appModules
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.di.firebaseModule
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@Category(CheckModuleTest::class)
class ModulesTest : KoinTest {

    private val unitTestModule = module(override = true) {
        single {
            mockk<Context> {
                every { getSystemService<ConnectivityManager>() } returns mockk()
            }
        }

        // Since Firebase is not mockable(?), just provide Repository
        single { mockk<Repository>() }
    }

    @Test
    fun checkAllModules() = checkModules {
        modules(appModules + unitTestModule)
    }
}
