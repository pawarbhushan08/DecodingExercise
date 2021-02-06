package com.example.decodingexercise.di.module

import com.example.decodingexercise.framework.DecoderViewModel
import com.example.decodingexercise.logic.PythonServices
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single { PythonServices(androidContext()) }
}

val viewModelModule = module {
    viewModel { DecoderViewModel(androidApplication(), get()) }
}