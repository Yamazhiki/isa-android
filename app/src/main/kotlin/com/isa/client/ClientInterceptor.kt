package com.ent.live.client

import com.ent.live.client.oauth.OauthAccessTokenType
import io.reactivex.Observer
import okhttp3.Interceptor
import okhttp3.Response

class ClientInterceptor(
        private val authType: OauthAccessTokenType,
        private val observer: Observer<Unit>
) : Interceptor {
    override fun intercept(p0: Interceptor.Chain?): Response {

        return if (p0 != null) {
            val request = p0.request()
                    .newBuilder()
                    .header(authType.key, authType.token)
                    .build()
            val response = p0.proceed(request)
            when (response.code()) {
                203 -> observer.onNext(Unit)
                else -> {
                }
            }
            response
        } else {
            Response.Builder().build()
        }

    }
}