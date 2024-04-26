package com.epicqueststudios.cocktails

import android.app.Application
import com.epicqueststudios.cocktails.di.component.AppComponent
import com.epicqueststudios.cocktails.di.component.DaggerAppComponent
import com.epicqueststudios.cocktails.di.module.AppModule


class MainApplication: Application() {
        lateinit var appComponent: AppComponent

        override fun onCreate() {
            super.onCreate()
            appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        }
}