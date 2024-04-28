package com.epicqueststudios.cocktails.di.module

import android.app.Application
import androidx.room.Room
import com.epicqueststudios.cocktails.data.db.CocktailDao
import com.epicqueststudios.cocktails.data.db.CocktailDatabase
import com.epicqueststudios.cocktails.data.repositories.CocktailRepository
import com.epicqueststudios.cocktails.data.repositories.CocktailRepositoryImpl
import com.epicqueststudios.cocktails.data.services.CocktailService
import com.epicqueststudios.cocktails.domain.CocktailOfTheDayUseCase
import com.epicqueststudios.cocktails.domain.DownloadCocktailsUseCase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [AppModule::class]
)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = (HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCocktailService(retrofit: Retrofit) = retrofit.create(CocktailService::class.java)


    @Singleton
    @Provides
    fun provideCocktailRepository(service: CocktailService, cocktailDao: CocktailDao): CocktailRepository = CocktailRepositoryImpl(service, cocktailDao)
    @Provides
    @Singleton
    fun provideDatabase(application: Application): CocktailDatabase {
        return Room.databaseBuilder(application, CocktailDatabase::class.java,
            CocktailDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCocktailDao(database: CocktailDatabase): CocktailDao {
        return database.cocktailDao()
    }
    @Singleton
    @Provides
    fun provideDownloadCocktailsUseCase(repository: CocktailRepository): DownloadCocktailsUseCase = DownloadCocktailsUseCase(repository)

    @Singleton
    @Provides
    fun provideCocktailOfTheDayUseCase(repository: CocktailRepository): CocktailOfTheDayUseCase = CocktailOfTheDayUseCase(repository)

    companion object  {
        const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    }


}
