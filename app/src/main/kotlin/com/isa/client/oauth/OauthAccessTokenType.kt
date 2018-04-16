package com.ent.live.client.oauth

enum class OauthTokenType(val p: String) {
    BEER("beer ")
}

interface OauthAccessTokenType {
    val key: String
    val token: String
    fun tokenHeader(): Map<String, String>

}