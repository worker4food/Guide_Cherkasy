package com.geekhub_android_2019.cherkasyguide.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel<M> : ViewModel() {
    protected val warnings by lazy { BroadcastChannel<M>(1) }
    protected val errors by lazy { BroadcastChannel<M>(1) }

    fun error(msg: M) = errors.offer(msg)
    fun warn(msg: M) = warnings.offer(msg)

    fun observeErrors(scope: CoroutineScope, fn: (M) -> Unit) = scope.launch {
        subscribeOn(errors, fn)
    }

    fun observeWarnings(scope: CoroutineScope, fn: (M) -> Unit) = scope.launch {
        subscribeOn(warnings, fn)
    }

    private suspend fun subscribeOn(ch: BroadcastChannel<M>, fn: (M) -> Unit) =
        withContext(Dispatchers.Main) {
            ch.openSubscription().consumeEach(fn)
        }
}
