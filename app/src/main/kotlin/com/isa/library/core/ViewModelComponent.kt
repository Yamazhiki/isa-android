package com.isa.library.core


import android.app.Activity
import android.app.Fragment
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.isa.library.DisposeBag
import com.isa.library.Open
import kotlin.reflect.KClass


interface ViewModelComponent<out T : ViewModelComponent.ViewModel> {

    val viewModel: T
        get() {
            val cls = this@ViewModelComponent::class.supertypes.first()
            val args = cls.arguments.first()
            val annotationsList = cls.javaClass.annotations

            var scope = ViewModelScope.SINGLETON

            for (item in annotationsList) {
                if (item is Produce) {
                    scope = item.type
                    break
                }
            }

            val gen: () -> T = {
                @Suppress("UNCHECKED_CAST")
                (args.type!!.classifier as KClass<*>).constructors.take(1).first().call() as T
            }

            @Suppress("UNCHECKED_CAST")
            return when (scope) {
                ViewModelScope.PROTOTYPE -> gen.invoke()
                else -> {
                    val key = args.type.toString()
                    if (map[key] == null) {
                        map[key] = gen.invoke()
                    }
                    map[key] as T
                }
            }
        }

    companion object {
        val map = mutableMapOf<String, ViewModel>()
    }

    interface ViewModel


    @Open
    open class ViewModelActivity<out T : ViewModel> : Activity(), ViewModelComponent<T>, LifecycleOwner {

        private val registry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = registry

        protected val disposeBag: DisposeBag by lazy {
            DisposeBag(this)
        }

        override fun setContentView(view: View?) {
            super.setContentView(view)
            bindViewModel()

        }

        override fun setContentView(layoutResID: Int) {
            super.setContentView(layoutResID)
            bindViewModel()
        }

        open fun bindViewModel() {}

        override fun onStart() {
            super.onStart()
            registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        override fun onStop() {
            super.onStop()
            registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }

        override fun onResume() {
            super.onResume()
            registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        override fun onDestroy() {
            super.onDestroy()
            registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    @Open
    open class ViewModelFragment<out T : ViewModel> : Fragment(), ViewModelComponent<T>, LifecycleOwner {

        private val registry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = registry

        protected val disposeBag: DisposeBag by lazy {
            DisposeBag(this)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            bindViewModel()
        }

        open fun bindViewModel() {

        }

        override fun onStart() {
            super.onStart()
            registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        override fun onStop() {
            super.onStop()
            registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }

        override fun onResume() {
            super.onResume()
            registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        override fun onDestroy() {
            super.onDestroy()
            registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }

    }
}