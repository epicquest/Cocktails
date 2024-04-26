package com.epicqueststudios.cocktails.di.component

import android.app.Application
import android.content.Context
import com.epicqueststudios.cocktails.MainActivity
import com.epicqueststudios.cocktails.di.module.AppModule
import com.epicqueststudios.cocktails.di.module.VMFactoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, VMFactoryModule::class])
interface AppComponent {
    fun provideContext(): Context
    fun provideApplication(): Application
    fun inject(mainActivity: MainActivity)
}