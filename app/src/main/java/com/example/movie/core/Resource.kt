package com.example.movie.core

import java.lang.Exception

sealed class Resource<out T> {
    //clase sellada para retornar los estados
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}