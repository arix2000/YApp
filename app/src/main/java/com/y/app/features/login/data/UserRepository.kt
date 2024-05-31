package com.y.app.features.login.data

import com.y.app.core.local.DataStoreManager
import com.y.app.core.network.ApiResponse
import com.y.app.core.network.ApiService
import com.y.app.core.network.BaseRepository
import com.y.app.features.login.data.models.RegistrationResponse
import com.y.app.features.login.data.models.RegistrationResult
import com.y.app.features.login.data.models.User
import com.y.app.features.registration.data.UserBody
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest

class UserRepository(val apiService: ApiService, val dataStore: DataStoreManager) : BaseRepository() {

    suspend fun loginUser(email: String, password: String): ApiResponse<User> {
        /*val response = makeHttpRequest { apiService.login(Credentials(email, hashPassword(password))) }
        if (response.isSuccess())
            saveUser(response.data!!)
        return response*/
        delay(2000)
        val response = if (email == "admin")
            ApiResponse.Success(User(1, "ADAM", "STANCZYK", email, "#00FF00"))
        else ApiResponse.Error("Wrong Password")
        if (response.isSuccess())
            saveUser(response.data!!)
        return response
    }

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    suspend fun registerUser(user: UserBody): ApiResponse<RegistrationResponse> {
        //val userHashed = user.copy(password = hashPassword(user.password))
        //return makeHttpRequest { apiService.register(userHashed) }
        delay(2000)
        return when (user.email) {
            "emailtaken@gmail.com" -> ApiResponse.Success(RegistrationResponse(RegistrationResult.EMAIL_TAKEN))
            "error@gmail.com" -> ApiResponse.Error("Something went wrong")
            else -> ApiResponse.Success(RegistrationResponse(RegistrationResult.OK))
        }
    }

    suspend fun getUser(userId: Int): ApiResponse<User> {
        //return makeHttpRequest { apiService.getUser(userId) }
        delay(2000)
        return ApiResponse.Success(User(1, "ADAM", "STANCZYK", "email@email.gmail", "#00FF00"))
    }

    private suspend fun saveUser(user: User) {
        dataStore.saveUser(user)
    }

    suspend fun getUser(): Flow<User?> {
        return dataStore.user
    }

}