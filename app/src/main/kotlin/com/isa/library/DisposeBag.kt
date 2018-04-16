package com.isa.library

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

fun Disposable.disposedBy(bag: DisposeBag) = bag.add(this)

class DisposeBag @JvmOverloads constructor(owner: LifecycleOwner,
                                           private val event: Lifecycle.Event = Lifecycle.Event.ON_STOP)
    : Disposable, DisposableContainer, LifecycleObserver {

    @JvmOverloads constructor(resources: Iterable<Disposable>,
                              owner: LifecycleOwner,
                              event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY)
            : this(owner, event) {

        resources.forEach { composite.add(it) }
    }

    private val lifecycle = owner.lifecycle

    private val composite by lazy { CompositeDisposable() }

    init {
        lifecycle.addObserver(this)
    }

    override fun isDisposed() = composite.isDisposed

    override fun dispose() {
        lifecycle.removeObserver(this)
        composite.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        dispose()
    }

    override fun add(d: Disposable) = composite.add(d)

    override fun remove(d: Disposable) = composite.remove(d)

    override fun delete(d: Disposable) = composite.delete(d)
}