package com.adrianbucayan.myrecipeapp.presentation.ui

import android.app.Application
import com.adrianbucayan.myrecipeapp.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyRecipeApplication : Application() {

    @Inject
    lateinit var timberTree: Timber.Tree

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}