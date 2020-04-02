package com.geekhub_android_2019.cherkasyguide.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class EventChannell<M> : BroadcastChannel<M> by BroadcastChannel(1) {

    fun observe(scope: CoroutineScope, fn: (M) -> Unit) = scope.launch(Dispatchers.Main) {
        openSubscription().consumeEach(fn)
    }

    fun observe(owner: LifecycleOwner, fn: (M) -> Unit) =
        observe(owner.lifecycleScope, fn)
}
