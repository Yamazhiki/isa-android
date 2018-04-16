package com.ent.live.client

class ServerConfig(
        override val apiBaseUrl: String,
        override val webBaseUrl: String
) : IServerConfig {
    companion object {
        val simulation: ServerConfig
            get() = ServerConfig("https://api.github.com/", "")

        val dev: ServerConfig
            get() = ServerConfig("https://api.github.com/", "")

        val production: ServerConfig
            get() = ServerConfig("https://api.github.com/", "")
    }
}