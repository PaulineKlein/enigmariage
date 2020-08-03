package com.pklein.mariage

import android.app.Application
import android.content.Context

class MariageApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Application
        fun getAppContext(): Context {
            return instance.applicationContext
        }
    }
}