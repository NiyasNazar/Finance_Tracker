package com.pas.financetracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp()
class App : Application() {





    override fun onCreate() {
        super.onCreate()
        sInstance = this

    }

    companion object {
        var sInstance: App? = null
    }
}