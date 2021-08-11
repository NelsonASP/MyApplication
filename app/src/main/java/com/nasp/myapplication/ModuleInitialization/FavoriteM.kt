package com.nasp.myapplication.ModuleInitialization


import com.nasp.myapplication.favorites.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteM = module {
    viewModel { FavoriteViewModel(giphyRepository = get()) }
}