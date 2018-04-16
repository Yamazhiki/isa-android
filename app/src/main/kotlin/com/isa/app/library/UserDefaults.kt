package com.isa.app.library

interface UserDefaults {
    fun <T> setObject(key: String, value: T)
    fun <T> getObject(byKey: String): T
}