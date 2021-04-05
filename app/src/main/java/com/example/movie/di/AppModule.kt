package com.example.movie.di

import android.content.Context
import androidx.room.Room
import com.example.movie.data.local.AppDatabase
import com.example.movie.data.local.MovieDao
import com.example.movie.domain.MovieService
import com.example.movie.utils.MovieConstants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Tenemos que decirle donde va a vivir las instancias del modulo, atachandose al ciclo de vida del componente
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    //Room proveemos la manera de crear las instancias
    @Singleton
    @Provides
    fun provideRoomInstance(
        //para crear el contexto
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, AppDatabase::class.java,
        "movie_table"
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    //Retrofit
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(MovieConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

}