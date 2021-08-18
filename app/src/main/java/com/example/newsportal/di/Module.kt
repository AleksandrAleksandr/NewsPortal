package com.example.newsportal.di

import android.app.Application
import androidx.room.Room
import com.example.newsportal.data.NewsRepository
import com.example.newsportal.data.remote.NewsService
import com.example.newsportal.app.topnews.NewsViewModel
import com.example.newsportal.data.local.*
import com.example.newsportal.data.remote.IRemoteDataSource
import com.example.newsportal.data.remote.NetworkMapper
import com.example.newsportal.data.remote.RemoteDataSource
import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.usecases.GetNewsUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
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
    single { GetNewsUseCase(get()) }
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
    fun provideNewsRepository(remote: IRemoteDataSource, local: ILocalDataSource): INewsRepository {
        return NewsRepository(remote, local)
    }

    single { DatabaseMapper() }
    single { NetworkMapper() }
    single<ILocalDataSource> { LocalDataSource(get(), get()) }
    single<IRemoteDataSource> { RemoteDataSource(get(), get()) }

    single { provideNewsRepository(get(), get()) }
}