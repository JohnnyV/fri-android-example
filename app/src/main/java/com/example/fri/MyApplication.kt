package com.example.fri

import android.app.Application
import android.util.Log

private const val TAG = "MyApplication"

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application instance created")
    }
}