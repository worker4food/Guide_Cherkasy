package com.geekhub_android_2019.cherkasyguide.di

import com.geekhub_android_2019.cherkasyguide.common.AppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatchersModule = module {
    single {
        AppDispatchers(
            main = Dispatchers.Main,
            default = Dispatchers.Default,
            io = Dispatchers.IO,
            unconfined = Dispatchers.Unconfined
        )
    }
}
