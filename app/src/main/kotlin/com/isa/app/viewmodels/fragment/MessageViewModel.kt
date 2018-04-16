package com.ent.live.app.viewmodels.fragment

import com.isa.app.library.AppEnvironment
import com.ent.live.app.model.User
import com.isa.library.core.ViewModelComponent
import com.ent.live.library.ext.ui
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface MessageViewModel : ViewModelComponent.ViewModel {

    interface Inputs {
        fun username(value: String)
        fun login()
    }

    interface Outputs {
        val users: Observable<List<User>>
    }


    class ViewModel : Inputs, Outputs, MessageViewModel {

        private val loginSubject = PublishSubject.create<Boolean>()
        private val usernameSubject = PublishSubject.create<String>()

        val inputs: Inputs
            get() = this
        val outputs: Outputs
            get() = this


        /*inputs*/
        override fun login() {
            loginSubject.onNext(false)
        }

        override fun username(value: String) {
            usernameSubject.onNext(value)
        }


        /*outputs*/
        override val users: Observable<List<User>>
            get() = AppEnvironment.current.api.users().ui()


    }
}