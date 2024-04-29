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
import com.epicqueststudios.cocktails.domain.CocktailsUseCase
import com.epicqueststudios.cocktails.domain.DownloadCocktailsUseCase
import com.epicqueststudios.cocktails.presentation.SearchState
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
                        private val cocktailsUseCase: CocktailsUseCase
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
    private val _selectedCocktail = mutableStateOf<CocktailModel?>(null)
    val selectedCocktail: State<CocktailModel?> = _selectedCocktail

    private val _searchState = mutableStateOf<SearchState<List<CocktailModel>>>(SearchState.Idle())
    val searchState: State<SearchState<List<CocktailModel>>> = _searchState
    fun searchCocktails(searchTerm: String) {
        viewModelScope.launch {
            try {
                _searchState.value = SearchState.Loading()
                _cocktails.value = listOf()
                _cocktails.value = downloadImagesUseCase.getCocktails(searchTerm)
                _searchState.value = SearchState.Success(_cocktails.value)
            } catch (e: Exception) {
                _searchState.value = SearchState.Error(e.message)
            }
        }
    }
    fun getCocktailOfTheDay() {
        viewModelScope.launch {
            try {
                val cocktailOfTheDay = cocktailsUseCase.getCocktailOfTheDay()
                //_cocktails.value = cocktailOfTheDay
                _cocktailOfTheDay.value = cocktailOfTheDay
            } catch (e: Exception) {
                Timber.e(e)
            }

        }
    }
    fun getCocktailOfTheDayAndFavorites() {
        viewModelScope.launch {
            try {
                val favorites = cocktailsUseCase.getFavourites()
                _cocktails.value = favorites
                cocktailsUseCase.getCocktailOfTheDay()?.also {
                    _cocktailOfTheDay.value = it
                    _cocktails.value = listOf(it).plus(favorites)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun onTextChange(text: String) {
        if (text.isNotEmpty()) {
            _cocktails.value = listOf()
        } else {
            getCocktailOfTheDayAndFavorites()
        }
        _searchState.value = SearchState.Idle()
    }

    fun insertCocktail(item: CocktailModel) {
        viewModelScope.launch {
            val res = cocktailsUseCase.insertCocktail(item)
            _selectedCocktail.value = item
        }
    }
    fun getCocktail(id: String) {
        viewModelScope.launch {
            _selectedCocktail.value = cocktailsUseCase.getCocktail(id)
        }
    }

    fun updateFavorites(item: CocktailModel?) {
        viewModelScope.launch {
            item?.let {
                it.isFavourite = !it.isFavourite
                _selectedCocktail.value = cocktailsUseCase.updateCocktail(it)
            }
        }
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        class Factory(
            private val app: Application,
            private val downloadCocktailsUseCase: DownloadCocktailsUseCase,
            private val cocktailsUseCase: CocktailsUseCase
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CocktailViewModel(
                    app,
                    Dispatchers.Main,
                    downloadCocktailsUseCase,
                    cocktailsUseCase
                ) as T
            }
        }

    }
}