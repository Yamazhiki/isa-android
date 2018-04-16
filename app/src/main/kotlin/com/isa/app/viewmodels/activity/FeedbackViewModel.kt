package com.isa.app.viewmodels.activity

import com.isa.library.core.ViewModelComponent
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject

interface FeedbackViewModel : ViewModelComponent.ViewModel {

    val inputs: Inputs
    val outputs: Outputs

    interface Inputs {
        fun submit()
        fun contentInputs(src: String)
    }

    interface Outputs {
        val result: Observable<String>
    }

    class ViewModel : Inputs, Outputs, FeedbackViewModel {

        private val resultSubject = PublishSubject.create<String>()
        private val contentSubject = PublishSubject.create<String>()
        private val submitSubject = PublishSubject.create<Unit>()

        override val inputs: Inputs
            get() = this

        override val outputs: Outputs
            get() = this

        override fun submit() {
            submitSubject.onNext(Unit)
        }

        override fun contentInputs(src: String) {
            contentSubject.onNext(src)
        }

        override val result: Observable<String>
            get() = submitSubject.withLatestFrom(contentSubject, { _, s -> s })
    }
}