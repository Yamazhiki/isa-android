package com.ent.live.client.oauth

class CommonAccessToken(override val token: String) : OauthAccessTokenType {
    override val key: String
        get() = "token"

    override fun tokenHeader(): Map<String, String> {
        return mapOf(Pair("token", token))
    }
}