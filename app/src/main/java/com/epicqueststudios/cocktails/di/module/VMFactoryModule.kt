package com.epicqueststudios.cocktails.di.module

import android.app.Application
import com.epicqueststudios.cocktails.domain.CocktailsUseCase
import com.epicqueststudios.cocktails.domain.SearchCocktailsUseCase
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
        searchCocktailsUseCase: SearchCocktailsUseCase,
        cocktailsUseCase: CocktailsUseCase
    ) = CocktailViewModel.Companion.Factory(app, searchCocktailsUseCase, cocktailsUseCase)

}