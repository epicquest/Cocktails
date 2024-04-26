package com.epicqueststudios.cocktails.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityContextModule(private val activity: Activity) {
    @Provides
    @Singleton
    fun provideActivity(): Activity = activity
}