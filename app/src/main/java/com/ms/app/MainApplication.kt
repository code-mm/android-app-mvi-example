package com.ms.app

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 开启协程
        GlobalScope.launch(Dispatchers.IO) {
            // 3init
        }
    }
}