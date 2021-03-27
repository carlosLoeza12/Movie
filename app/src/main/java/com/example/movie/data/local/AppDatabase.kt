package com.example.movie.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movie.data.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            instance?.let {
                Log.e("appDatabase", "Instancia creada")
            } ?: kotlin.run {
                instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    "movie_table"
                ).build()

            }
            return instance!!
        }
    }
}