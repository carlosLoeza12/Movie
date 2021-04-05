package com.example.movie.di

import com.example.movie.domain.MovieRepository
import com.example.movie.domain.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

// este modulo es para el viewModel, sobreviven a la rotacion de pantalla
//si ponemos el activityComponets se regenera
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindRepoImp(repository: MovieRepositoryImpl): MovieRepository

}