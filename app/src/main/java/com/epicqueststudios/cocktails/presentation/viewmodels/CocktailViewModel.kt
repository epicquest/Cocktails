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
import com.epicqueststudios.cocktails.domain.SearchCocktailsUseCase
import com.epicqueststudios.cocktails.presentation.models.Resource
import com.epicqueststudios.cocktails.presentation.models.SearchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CocktailViewModel(app: Application,
                        private var uiContext: CoroutineContext,
                        private val downloadImagesUseCase: SearchCocktailsUseCase,
                        private val cocktailsUseCase: CocktailsUseCase
    ) : ViewModel(), CoroutineScope {

    private var component: ViewModelComponent
    @Inject
    lateinit var repository: CocktailRepository
    private lateinit var job: Job

    init {
        component = DaggerViewModelComponent.builder()
            .appModule(AppModule(app))
            .vMFactoryModule(VMFactoryModule()).build()
        component.inject(this)

    }
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    private val _cocktails = mutableStateOf<List<Resource<CocktailModel>>>(emptyList())
    val cocktails: State<List<Resource<CocktailModel>>> = _cocktails
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
                _searchState.value = SearchState.Success(_cocktails.value.map { it.data!! })
            } catch (e: Exception) {
                _searchState.value = SearchState.Error(e.message)
            }
        }
    }
    fun getCocktailOfTheDayAndFavorites() {
        viewModelScope.launch {
            var favorites: List<Resource<CocktailModel>> = listOf()
            try {
                favorites = cocktailsUseCase.getFavourites()
                _cocktails.value = listOf(Resource.Loading<CocktailModel>()).plus(favorites)
                cocktailsUseCase.getCocktailOfTheDay().also {
                    _cocktails.value = listOf(it).plus(favorites)
                }
            } catch (e: Exception) {
                Timber.e(e)
                _cocktails.value = listOf<Resource<CocktailModel>>(Resource.Error(e.message)).plus(favorites)
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
            cocktailsUseCase.insertCocktail(item)
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
            private val searchCocktailsUseCase: SearchCocktailsUseCase,
            private val cocktailsUseCase: CocktailsUseCase
        ) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CocktailViewModel(
                    app,
                    Dispatchers.Main,
                    searchCocktailsUseCase,
                    cocktailsUseCase
                ) as T
            }
        }

    }
}