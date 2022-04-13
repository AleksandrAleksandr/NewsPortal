package com.example.newsportal.di

import android.app.Application
import androidx.room.Room
import com.example.newsportal.app.bookmarks.BookmarksViewModel
import com.example.newsportal.app.search.NewsSearchViewModel
import com.example.newsportal.data.NewsRepository
import com.example.newsportal.app.topnews.NewsViewModel
import com.example.newsportal.data.local.*
import com.example.newsportal.data.remote.*
import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.usecases.*
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
    viewModel { NewsViewModel(get(), get(), get()) }
    viewModel { NewsSearchViewModel(get(), get()) }
    viewModel { BookmarksViewModel(get(), get()) }
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

    fun provideBookmarksDao(database: Database): BookmarksDao {
        return database.bookmarksDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
    single { provideBookmarksDao(get()) }
}

val domainModule = module {
    single { GetNewsUseCase(get()) }
    single { GetNewsSearchUseCase(get()) }
    single { AddBookmarkUseCase(get()) }
    single { GetBookmarksUseCase(get()) }
    single { DeleteBookmarkUseCase(get()) }
    single { RefreshNewsUseCase(get()) }
}

val repositoryModule = module {

    single { ArticleDatabaseMapper() }
    single { ArticleNetworkMapper() }
    single<ILocalDataSource> { LocalDataSource(get(),get(), get()) }
    single<IRemoteDataSource> { RemoteDataSource(get(), get()) }

    single<INewsRepository> { NewsRepository(get(), get()) }
}