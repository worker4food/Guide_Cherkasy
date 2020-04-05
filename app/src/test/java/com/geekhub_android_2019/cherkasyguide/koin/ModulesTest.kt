package com.geekhub_android_2019.cherkasyguide.koin

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.geekhub_android_2019.cherkasyguide.appModules
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@Category(CheckModuleTest::class)
class ModulesTest : KoinTest {

    private val ctxM = module {
        single {
            mockk<Context> {
                every { getSystemService<ConnectivityManager>() } returns mockk()
            }
        }
    }

    @Test
    fun checkAllModules() = checkModules {
        modules(ctxM + appModules)
    }
}
