package com.pklein.mariage

import android.app.Application
import android.content.Context
import com.google.firebase.appcheck.FirebaseAppCheck
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MariageApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        installAppCheck()
    }

    private fun installAppCheck() {
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(appCheckProviderFactory())
    }

    companion object {
        lateinit var instance: Application

        fun getAppContext(): Context = instance.applicationContext
    }
}
