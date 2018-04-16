package com.isa.app.viewmodels.activity

import com.ent.live.app.model.User
import com.isa.app.library.AppEnvironment
import com.isa.library.core.ViewModelComponent
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject

interface LoginViewModel : ViewModelComponent.ViewModel {

    interface Inputs {
        fun username(src: String)
        fun password(src: String)
        fun login()
    }

    interface Outputs {
        val result: Observable<List<User>>
        val canLogin: Observable<Boolean>
    }


    class ViewModel : Inputs, Outputs, LoginViewModel {

        private val loginSubject = PublishSubject.create<Unit>()
        private val usernameSubject = PublishSubject.create<String>()
        private val passwordSubject = PublishSubject.create<String>()

        val inputs: Inputs
            get() = this
        val outputs: Outputs
            get() = this


        /*inputs*/
        override fun login() {
            loginSubject.onNext(Unit)
        }

        override fun username(src: String) {
            usernameSubject.onNext(src)
        }

        override fun password(src: String) {
            passwordSubject.onNext(src)
        }


        /*outputs*/
        override val result: Observable<List<User>>
            get() = loginSubject.withLatestFrom(combineLatest(usernameSubject, passwordSubject, { a, b -> Pair(a, b) }))
                    .map { it.second }
                    .switchMap { AppEnvironment.current.api.users() }
        override val canLogin: Observable<Boolean>
            get() = combineLatest(usernameSubject, passwordSubject, { a, b -> Pair(a, b) })
                    .map { it.first.length > 7 && it.second.length > 6 }


    }
}