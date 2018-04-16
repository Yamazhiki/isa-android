package com.isa.app.library.api

import com.ent.live.app.model.User
import io.reactivex.Observable
import retrofit2.http.*

interface ApiClient {
    @POST("/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<String>

    @GET("/user/{id}")
    fun user(@Path("id") id: Int): Observable<User>

    @GET("/users")
    fun users(): Observable<List<User>>
}