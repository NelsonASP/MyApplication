package com.nasp.myapplication.ModuleInitialization

import com.nasp.myapplication.utils.UrlBase.Companion.BASE_URL
import com.nasp.myapplication.data.remote.giphy.ConfigURL
import com.nasp.myapplication.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.nasp.myapplication.data.remote.giphy.repository.GiphyRepository
import org.koin.dsl.module

val giphyModule = module {

    single { provideRetrofit(okHttpClient = get(), BASE_URL) }

    factory { createWebService<ConfigURL>(retrofit = get()) }

    single { GiphyRemoteDataSource(configURL = get()) }

    single { GiphyRepository(dao = get(), remoteSource = get()) }
}