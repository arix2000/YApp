package com.y.app.core.network

import com.y.app.features.home.data.models.Comment
import com.y.app.features.home.data.models.Post
import com.y.app.features.home.data.models.bodies.CommentBody
import com.y.app.features.home.data.models.bodies.CommentLikeBody
import com.y.app.features.home.data.models.bodies.PostBody
import com.y.app.features.home.data.models.bodies.PostLikeBody
import com.y.app.features.login.data.models.Credentials
import com.y.app.features.login.data.models.RegistrationResponse
import com.y.app.features.login.data.models.User
import com.y.app.features.registration.data.UserBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    suspend fun login(@Body credentials: Credentials): User

    @POST("register")
    suspend fun register(@Body user: UserBody): RegistrationResponse

    @GET("user/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    @GET("posts")
    suspend fun getPosts(@Query("filter") filter: String): List<Post>

    @GET("posts/{userId}")
    suspend fun getUserPosts(
        @Path("userId") userId: Int,
        @Query("filter") filter: String
    ): List<Post>

    @POST("post")
    suspend fun addPost(@Body post: PostBody)

    @GET("comments/{postId}")
    suspend fun getComments(@Path("postId") postId: Int): List<Comment>

    @POST("comments/{postId}")
    suspend fun getComments(@Path("postId") postId: Int, @Body comment: CommentBody)

    @POST("post/like")
    suspend fun likePost(@Body postLikeBody: PostLikeBody)

    @POST("comments/like")
    suspend fun likeComment(@Body commentLikeBody: CommentLikeBody)
}