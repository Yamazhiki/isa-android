package com.ent.live.client.oauth

class JwtAccessToken(override val token: String) : OauthAccessTokenType {

    override val key: String
        get() = "jwt"

    override fun tokenHeader(): Map<String, String> {
        return mapOf(Pair("jwt_token", ""))
    }
}