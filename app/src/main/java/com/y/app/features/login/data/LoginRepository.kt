package com.y.app.features.login.data

import com.y.app.core.network.ApiResponse
import com.y.app.core.network.ApiService
import com.y.app.core.network.BaseRepository
import com.y.app.features.login.data.models.User
import kotlinx.coroutines.delay
import java.security.MessageDigest

class LoginRepository(val apiService: ApiService) : BaseRepository() {

    suspend fun loginUser(email: String, password: String): ApiResponse<User> {
        //return makeHttpRequest { apiService.login(Credentials(email, hashPassword(password))) }
        delay(2000)
        return if (email == "admin" && password == "administrator")
            ApiResponse.Success(User(1, "ADAM", "STANCZYK", email, "#00FF00"))
        else ApiResponse.Error("Wrong Password")
    }

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}