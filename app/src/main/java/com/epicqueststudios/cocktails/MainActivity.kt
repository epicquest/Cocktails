package com.epicqueststudios.cocktails

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.epicqueststudios.cocktails.di.component.ActivityComponent
import com.epicqueststudios.cocktails.di.component.AppComponent
import com.epicqueststudios.cocktails.di.component.DaggerActivityComponent
import com.epicqueststudios.cocktails.di.module.ActivityContextModule
import com.epicqueststudios.cocktails.di.module.AppModule
import com.epicqueststudios.cocktails.di.module.VMFactoryModule
import com.epicqueststudios.cocktails.presentation.viewmodels.CocktailViewModel
import com.epicqueststudios.cocktails.presentation.views.CocktailListScreen
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    private lateinit var activityComponent: ActivityComponent
    @Inject
    lateinit var mainViewModelFactory: CocktailViewModel.Companion.Factory
    private val cocktailViewModel: CocktailViewModel by viewModels {
        mainViewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent = DaggerActivityComponent.builder()
            .appModule(AppModule(application))
            .activityContextModule(ActivityContextModule(this))
            .vMFactoryModule(VMFactoryModule())
            .build()
        activityComponent.inject(this)

        setContent {
            /*viewModel = remember {
                val cocktailService = Retrofit.Builder()
                    .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/") // Update with Retrofit API base URL
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CocktailService::class.java)
                val roomDatabase = CocktailDatabase.buildDatabase(this)
                val cocktailDao = roomDatabase.cocktailDao()

                CocktailViewModel(CocktailRepository(cocktailService, cocktailDao))
            }*/

            CocktailListScreen(viewModel = cocktailViewModel)

        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val v = super.onCreateView(name, context, attrs)
         v
        cocktailViewModel.searchCocktails("rum")
        return v
    }
}
fun ComponentActivity.getApplicationComponent(): AppComponent? {
    application?.let {
        return (it as? MainApplication)?.appComponent
    }
    return null
}
