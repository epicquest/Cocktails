package com.epicqueststudios.cocktails.di.module

import android.app.Application
import com.epicqueststudios.cocktails.domain.CocktailOfTheDayUseCase
import com.epicqueststudios.cocktails.domain.DownloadCocktailsUseCase
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module(includes = [AppModule::class, NetworkModule::class])

class VMFactoryModule {
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun provideMainViewModelFactory(
        app: Application,
        downloadCocktailsUseCase: DownloadCocktailsUseCase,
        cocktailOfTheDayUseCase: CocktailOfTheDayUseCase
    ) = CocktailViewModel.Companion.Factory(app, downloadCocktailsUseCase, cocktailOfTheDayUseCase)

}