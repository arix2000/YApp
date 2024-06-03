package com.y.app.core.di

import com.y.app.core.local.DataStoreManager
import com.y.app.core.navigation.Navigator
import com.y.app.core.network.RetrofitClient
import com.y.app.features.home.data.PostRepository
import com.y.app.features.home.data.ui.HomeViewModel
import com.y.app.features.login.data.UserRepository
import com.y.app.features.login.ui.state.LoginViewModel
import com.y.app.features.registration.ui.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DataStoreManager(get()) }

    single { Navigator() }

    single { RetrofitClient.create() }

    factory { UserRepository(get(), get()) }

    factory { PostRepository(get(), get()) }

    viewModel { LoginViewModel(get()) }

    viewModel { RegistrationViewModel(get()) }

    viewModel { HomeViewModel(get()) }
}