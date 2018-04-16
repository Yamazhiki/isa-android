package com.isa.app.library.api

import com.ent.live.app.model.User
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

class MockApiClient : ApiClient {
    override fun login(username: String, password: String): Observable<String> {
        return Observable.just(if (username == "Yamazhiki".toLowerCase() && password == "3689126") "TOKEN" else "")
    }

    override fun user(id: Int): Observable<User> {
        return Observable.just(User.template)
    }

    override fun users(): Observable<List<User>> {
        return arrayListOf(listOf(User.template)).toObservable()
    }
}