package com.example.movie.utils

import android.content.Context

class sharedPreferences(val context: Context) {

    val SHARED_NAME= "Movie"
    val storage = context.getSharedPreferences(SHARED_NAME  , 0)

    fun saveState(state: Boolean){
        storage.edit().putBoolean("state",state).apply()
    }
    fun getState()=storage.getBoolean("state", false)


    fun savePosition(position: Int){
        storage.edit().putInt("position", position).apply()
    }
    fun getPosition() = storage.getInt("position",0)


    fun saveTypeMovie(type: String){
        storage.edit().putString("type", type).apply()
    }
    fun getTypeMovie() = storage.getString("type","no")


    fun deletePreferences(){
        storage.edit().clear().apply()
    }

}