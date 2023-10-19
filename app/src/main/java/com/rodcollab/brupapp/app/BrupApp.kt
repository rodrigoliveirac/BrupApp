package com.rodcollab.brupapp.app

import android.app.Application
import com.rodcollab.brupapp.di.AppContainer
import com.rodcollab.brupapp.di.AppContainerImpl

class BrupApp : Application() {

    lateinit var appContainer : AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl()
    }
}

