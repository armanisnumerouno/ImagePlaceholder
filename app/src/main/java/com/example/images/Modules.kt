package com.example.images

import com.example.images.network.Api
import com.example.images.repository.albumpreview.AlbumPreviewRepository
import com.example.images.repository.albumpreview.AlbumPreviewRepositoryImpl
import com.example.images.repository.albumslist.AlbumsListRepository
import com.example.images.repository.albumslist.AlbumsListRepositoryImpl
import com.example.images.view.albumpreview.AlbumPreviewViewModel
import com.example.images.view.albumpreview.AlbumPreviewViewModelImpl
import com.example.images.view.albumslist.AlbumsListViewModel
import com.example.images.view.albumslist.AlbumsListViewModelImpl
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel<AlbumsListViewModel> { AlbumsListViewModelImpl(get()) }
    viewModel<AlbumPreviewViewModel> { AlbumPreviewViewModelImpl(get()) }
}

val repositoryModule = module {
    single<AlbumsListRepository> { AlbumsListRepositoryImpl(get()) }
    single<AlbumPreviewRepository> { AlbumPreviewRepositoryImpl(get()) }
}

val networkModule = module {
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun provideApiService(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideApiService(get()) }
    single { provideGson() }
}