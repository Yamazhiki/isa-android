package com.isa.app.library

import com.ent.live.app.library.UserEvents
import com.ent.live.app.model.User
import com.ent.live.client.oauth.CommonAccessToken
import com.ent.live.client.oauth.OauthAccessTokenType
import com.isa.app.library.api.ApiClient
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.locks.ReentrantReadWriteLock

class AppEnvironment {
    companion object {
        private val stack = mutableListOf(Environment())
        private val lock = ReentrantReadWriteLock()
        private val userEventsSubject = PublishSubject.create<UserEvents>()
        private var envType: EnvironmentType = EnvironmentType.DEV
        private val tokenExpiredSubject = PublishSubject.create<Unit>()

        @JvmStatic
        val current: Environment
            get() {
                lock.readLock().lock()
                val rlt = stack.last()
                lock.readLock().unlock()
                return rlt
            }

        val userEvents: Observable<UserEvents>
            get() = Observable.merge(userEventsSubject, tokenExpiredSubject.map { UserEvents.TOKEN_EXPIRED })

        @JvmStatic
        fun updateUser(user: User) {
            replace(user = user)
        }

        @JvmStatic
        fun auth(accessToken: OauthAccessTokenType) {
            replace(api = generateEnv(accessToken))
        }

        @JvmStatic
        fun switchEnv(type: EnvironmentType) {
            envType = type
            replace(api = generateEnv())
            userEventsSubject.onNext(UserEvents.ENV_CHANGED)
        }

        private fun replace(
                user: User = current.currentUser,
                api: ApiClient = current.api
        ) {
            replace(Environment(user, api))
        }

        private fun replace(environment: Environment) {
            lock.writeLock().lock()
            stack.add(environment)
            stack.removeAt(stack.count() - 2)
            lock.writeLock().unlock()
        }

        private fun generateEnv(accessToken: OauthAccessTokenType = CommonAccessToken("")): ApiClient {
            return envType.api(accessToken, tokenExpiredSubject)
        }

        /**
         * 持久化恢复数据
         */
        @JvmStatic
        fun fromStorage(): Environment {
            return Environment()
        }
    }
}