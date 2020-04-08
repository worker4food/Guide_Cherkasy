package com.geekhub_android_2019.cherkasyguide

import android.app.Application
import com.geekhub_android_2019.cherkasyguide.di.*
import com.geekhub_android_2019.cherkasyguide.maputils.MapHelper
import com.geekhub_android_2019.cherkasyguide.routeapi.DirectionsApiFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

val appModules = listOf(
    dispatchersModule,
    netHelperModule,
    repositoryModule,
    viewModelsModule
)

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        DirectionsApiFactory.init(this)
        MapHelper.init(this)

        startKoin {
            androidLogger(if(BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@AppClass)

            modules(appModules + firebaseModule)
        }
    }
}
