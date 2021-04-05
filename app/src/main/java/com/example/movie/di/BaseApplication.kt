package com.example.movie.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
//contenedor del grafico de dependencias
class BaseApplication: Application() {
}