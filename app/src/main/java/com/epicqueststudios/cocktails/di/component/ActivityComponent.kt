package com.epicqueststudios.cocktails.di.component

import android.app.Activity
import android.app.Application
import android.content.Context
import com.epicqueststudios.cocktails.MainActivity
import com.epicqueststudios.cocktails.di.module.ActivityContextModule
import com.epicqueststudios.cocktails.di.module.AppModule
import com.epicqueststudios.cocktails.di.module.NetworkModule
import com.epicqueststudios.cocktails.di.module.VMFactoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ActivityContextModule::class, VMFactoryModule::class, NetworkModule::class])
interface ActivityComponent {

    fun provideContext(): Context
    fun provideActivity(): Activity
    fun provideApplication(): Application
    fun inject(activity: MainActivity)
}