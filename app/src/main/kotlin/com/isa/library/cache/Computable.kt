package com.isa.library.cache

interface Computable<in K, out V> {
    fun get(k: K): V
}