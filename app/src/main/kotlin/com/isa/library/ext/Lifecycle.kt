package com.ent.live.library.ext

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable

fun Disposable.disposedWith(owner: LifecycleOwner,
                            event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY) {

    owner.lifecycle.addObserver(object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            if (event == Lifecycle.Event.ON_PAUSE) removeObserverAndDispose(owner)

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            if (event == Lifecycle.Event.ON_STOP) removeObserverAndDispose(owner)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            if (event == Lifecycle.Event.ON_DESTROY) removeObserverAndDispose(owner)
        }

        fun removeObserverAndDispose(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            dispose()
        }
    })
}