package com.example.newsportal.di

import android.app.Application
import androidx.room.Room
import com.example.newsportal.repository.NewsRepository
import com.example.newsportal.network.NewsService
import com.example.newsportal.room.Database
import com.example.newsportal.room.NewsDao
import com.example.newsportal.viewmodel.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideRetrofit() }
    single { provideNewsService(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(NewsService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideNewsService(retrofit: Retrofit): NewsService {
    return retrofit.create(NewsService::class.java)
}

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): Database {
        return Room.databaseBuilder(application, Database::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: Database): NewsDao {
        return database.newsDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideNewsRepository(apiService: NewsService, dao: NewsDao): NewsRepository {
        return NewsRepository(apiService, dao)
    }

    single { provideNewsRepository(get(), get()) }
}