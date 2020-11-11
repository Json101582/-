package com.mo.aad

import android.app.Application
import com.mo.aad.koin.remoteModule
import com.mo.aad.koin.uiModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@FlowPreview
@ExperimentalCoroutinesApi
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(uiModule, remoteModule)
        }
    }
}