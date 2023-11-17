package com.rodcollab.brupapp.app

import android.app.Application
import com.rodcollab.brupapp.di.AppContainerImpl

class BrupApp : Application() {

    lateinit var appContainer: AppContainerImpl
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(this)
    }
}

