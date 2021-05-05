package com.example.omdbmoviesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.data.SeriesShortSpecs


@Database(entities = [MovieShortSpecs::class, SeriesShortSpecs::class],
    version = 2)

abstract class MoviesSeriesDb: RoomDatabase() {

    abstract fun movieDao(): MovieDao?

    companion object{
        private var INSTANCE: MoviesSeriesDb? = null

        fun getAppDatabase(context: Context?): MoviesSeriesDb?{
            if(INSTANCE == null){
                INSTANCE = context?.let {
                    Room.databaseBuilder<MoviesSeriesDb>(
                        it.applicationContext, MoviesSeriesDb::class.java,"moviesseries"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}











