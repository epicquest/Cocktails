package com.epicqueststudios.cocktails.presentation.viewmodels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.epicqueststudios.cocktails.data.models.CocktailModel
import com.epicqueststudios.cocktails.data.repositories.CocktailRepository
import com.epicqueststudios.cocktails.di.component.DaggerViewModelComponent
import com.epicqueststudios.cocktails.di.component.ViewModelComponent
import com.epicqueststudios.cocktails.di.module.AppModule
import com.epicqueststudios.cocktails.di.module.VMFactoryModule
import com.epicqueststudios.cocktails.domain.CocktailOfTheDayUseCase
import com.epicqueststudios.cocktails.domain.DownloadCocktailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CocktailViewModel(app: Application,
                        private var uiContext: CoroutineContext,
                        private val downloadImagesUseCase: DownloadCocktailsUseCase,
                        private val cocktailOfTheDayUseCase: CocktailOfTheDayUseCase
    ) : ViewModel(), CoroutineScope {
    lateinit var component: ViewModelComponent
    @Inject
    lateinit var repository: CocktailRepository
    var ioCoroutineContext = Dispatchers.IO
    private lateinit var job: Job

    init {
        component = DaggerViewModelComponent.builder()
            .appModule(AppModule(app))
            .vMFactoryModule(VMFactoryModule()).build()
        component.inject(this)

    }
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    private val _cocktails = mutableStateOf<List<CocktailModel>>(emptyList())
    val cocktails: State<List<CocktailModel>> = _cocktails
    private val _cocktailOfTheDay = mutableStateOf<CocktailModel?>(null)
    val cocktailOfTheDay: State<CocktailModel?> = _cocktailOfTheDay


    fun searchCocktails(searchTerm: String) {
        viewModelScope.launch {
            _cocktails.value = downloadImagesUseCase.getCocktails(searchTerm)
        }
    }
    fun getCocktailOfTheDay() {
        viewModelScope.launch {
            try {
                _cocktailOfTheDay.value = cocktailOfTheDayUseCase.getCocktail()
            } catch (e: Exception) {
                Timber.e(e)
            }

        }
    }
    companion object {

        @Suppress("UNCHECKED_CAST")
        class Factory(
            private val app: Application,
            private val downloadCocktailsUseCase: DownloadCocktailsUseCase,
            private val cocktailOfTheDayUseCase: CocktailOfTheDayUseCase
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CocktailViewModel(
                    app,
                    Dispatchers.Main,
                    downloadCocktailsUseCase,
                    cocktailOfTheDayUseCase
                ) as T
            }
        }

    }
}