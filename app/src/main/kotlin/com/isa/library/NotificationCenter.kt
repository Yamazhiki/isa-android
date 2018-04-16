package com.isa.library

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class NotificationCenter {
    private val subject = PublishSubject.create<Notification<*>>()

    val notification: Observable<Notification<*>>
        get() = subject


    fun <T> post(notification: Notification<T>) {
        subject.onNext(notification)
    }


    companion object {
        val default: NotificationCenter by lazy {
            NotificationCenter()
        }
    }


    data class Notification<T>(
            val name: String,
            val data: T)

}