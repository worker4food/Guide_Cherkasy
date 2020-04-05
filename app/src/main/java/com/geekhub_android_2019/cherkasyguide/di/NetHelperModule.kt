package com.geekhub_android_2019.cherkasyguide.di

import com.geekhub_android_2019.cherkasyguide.network.NetHelper
import org.koin.dsl.module

val netHelperModule = module {
    single { NetHelper(get()) }
}
