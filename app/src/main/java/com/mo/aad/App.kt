package com.mo.aad

import android.app.Application
import com.mo.aad.koin.persistenceModule
import com.mo.aad.koin.remoteModule
import com.mo.aad.koin.uiModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@FlowPreview
@ExperimentalCoroutinesApi
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(remoteModule)
            modules(uiModule)
            modules(persistenceModule)
//            modules(uiModule, remoteModule, persistenceModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}