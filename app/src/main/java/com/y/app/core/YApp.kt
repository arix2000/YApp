package com.y.app.core

import android.app.Application
import com.y.app.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YApp)
            modules(appModule)
        }
    }
}