package com.nasp.myapplication.ModuleInitialization

import com.nasp.myapplication.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel { HomeViewModel(giphyRepository = get()) }
}