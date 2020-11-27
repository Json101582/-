package com.mo.aad.koin

import com.mo.aad.features.main.repository.MainRepository
import com.mo.aad.features.main.viewmodel.MainViewModel
import com.mo.aad.features.poked.repository.PokedNewRepository
import com.mo.aad.features.poked.repository.PokedRepository
import com.mo.aad.features.poked.viewmodel.PokedNewViewModel
import com.mo.aad.features.poked.viewmodel.PokedViewModel
import com.mo.aad.features.submission.repository.SubmissionRepository
import com.mo.aad.features.submission.viewmodel.SubmissionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val uiModule = module {
    single { MainRepository(get(), get()) }
    viewModel { MainViewModel(get()) }

    single { SubmissionRepository(get()) }
    viewModel { SubmissionViewModel(get()) }

    single { PokedRepository(get()) }
    viewModel { PokedViewModel(get()) }

    single { PokedNewRepository(get(),get()) }
    viewModel { PokedNewViewModel(get()) }
}