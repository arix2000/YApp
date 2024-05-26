package com.y.app.core.di

import com.y.app.core.local.DataStoreManager
import com.y.app.core.navigation.Navigator
import com.y.app.core.network.RetrofitClient
import org.koin.dsl.module

val appModule = module {
    single { DataStoreManager(get()) }

    single { Navigator() }

    single { RetrofitClient.create() }
}