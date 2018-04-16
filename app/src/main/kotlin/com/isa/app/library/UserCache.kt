package com.isa.app.library

import com.ent.live.app.model.User
import com.isa.library.cache.Computable

class UserCache : Computable<Int, User> {

    private val cache = HashMap<Int, User>()

    override fun get(k: Int): User {
        Thread.sleep(5000)
        return if (cache.containsKey(k)) {
            cache[k]!!
        } else {
            val u = User.template
            cache[k] = u
            u
        }
    }
}