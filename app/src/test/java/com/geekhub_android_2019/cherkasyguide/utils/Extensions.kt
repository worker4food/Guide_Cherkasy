package com.geekhub_android_2019.cherkasyguide.utils

import com.geekhub_android_2019.cherkasyguide.common.AppDispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest


fun CoroutineTestRule.runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
    testDispatcher.runBlockingTest(block)
}

fun TestCoroutineDispatcher.asAppDispatchers() =
    AppDispatchers(
        main = this,
        io = this,
        default = this,
        unconfined = this
    )
