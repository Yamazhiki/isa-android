package com.isa.app.library

import com.ent.live.client.ClientInterceptor
import com.ent.live.client.IServerConfig
import com.ent.live.client.ServerConfig
import com.ent.live.client.oauth.OauthAccessTokenType
import com.isa.app.library.api.ApiClient
import com.isa.app.library.api.MockApiClient
import io.reactivex.Observer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

enum class EnvironmentType {
    SIMULATION,
    DEV,
    PRODUCTION
}

fun EnvironmentType.serverConfig(): IServerConfig {
    return when (this) {
        EnvironmentType.SIMULATION -> ServerConfig.simulation
        EnvironmentType.DEV -> ServerConfig.dev
        else -> ServerConfig.production
    }
}

fun EnvironmentType.api(accessToken: OauthAccessTokenType, observer: Observer<Unit>): ApiClient {
    return when (this) {
        EnvironmentType.SIMULATION -> MockApiClient()
        else -> {
            val client = OkHttpClient.Builder()
                    .addInterceptor(ClientInterceptor(accessToken, observer))
                    .build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(serverConfig().apiBaseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(ApiClient::class.java)
        }
    }
}