package com.y.app.features.home.data

import com.y.app.core.local.DataStoreManager
import com.y.app.core.network.ApiResponse
import com.y.app.core.network.ApiService
import com.y.app.core.network.BaseRepository
import com.y.app.features.home.data.models.Post
import com.y.app.features.home.data.models.PostFilterEnum
import com.y.app.features.home.data.models.bodies.PostBody
import com.y.app.features.home.data.models.bodies.PostLikeBody
import com.y.app.features.login.data.models.User
import kotlinx.coroutines.flow.Flow

class PostRepository(private val apiService: ApiService, private val dataStore: DataStoreManager) :
    BaseRepository() {

    suspend fun getPosts(filterEnum: PostFilterEnum): ApiResponse<List<Post>> {
        return makeHttpRequest { apiService.getPosts(filterEnum.key) }
    }

    fun getUser(): Flow<User?> {
        return dataStore.user
    }

    suspend fun likePost(likeBody: PostLikeBody): ApiResponse<Unit> {
        return makeHttpRequest { apiService.likePost(likeBody) }
    }

    suspend fun addPost(postBody: PostBody): ApiResponse<Unit> {
        return makeHttpRequest { apiService.addPost(postBody) }
    }
}