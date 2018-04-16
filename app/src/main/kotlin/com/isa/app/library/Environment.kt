package com.isa.app.library

import com.ent.live.app.model.User
import com.google.gson.Gson
import com.isa.app.library.api.ApiClient
import com.isa.app.library.api.MockApiClient

class Environment(
        val currentUser: User = User.template,
        val api: ApiClient = MockApiClient(),
        val gson: Gson = Gson()
) {
    companion object {
    }
}