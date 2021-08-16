package com.example.newsportal

import android.app.Application
import com.example.newsportal.di.appModule
import com.example.newsportal.di.databaseModule
import com.example.newsportal.di.repositoryModule
import com.example.newsportal.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            // declare modules
            modules(appModule, viewModelModule, databaseModule, repositoryModule)
        }
    }
}