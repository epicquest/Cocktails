package com.epicqueststudios.cocktails.di.component


import com.epicqueststudios.cocktails.di.module.NetworkModule
import com.epicqueststudios.cocktails.di.module.VMFactoryModule
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [VMFactoryModule::class, NetworkModule::class])
interface ViewModelComponent {
    fun inject(viewModel: CocktailViewModel)
}