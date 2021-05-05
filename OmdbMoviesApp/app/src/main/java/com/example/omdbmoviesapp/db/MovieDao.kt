package com.example.omdbmoviesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.data.SeriesShortSpecs

@Dao
interface MovieDao {

    //Movies
    @Query("SELECT * FROM movieinfo")
    fun getAll(): LiveData<List<MovieShortSpecs>>

    @Query("SELECT * FROM movieinfo")
    fun getAllinList(): List<MovieShortSpecs>

    @Query("SELECT * FROM movieinfo WHERE id= :id")
    suspend fun getById(id:String): MovieShortSpecs

    @Update
    suspend fun update(movie: List<MovieShortSpecs>)

    @Insert
    suspend fun insert(movie: MovieShortSpecs)

    @Delete
    suspend fun delete(movie: MovieShortSpecs)

    //Series
    @Query("SELECT * FROM seriesinfo")
    fun getAllSeries(): LiveData<List<SeriesShortSpecs>>

    @Query("SELECT * FROM seriesinfo")
    fun getAllinListSeries(): List<SeriesShortSpecs>

    @Query("SELECT * FROM seriesinfo WHERE id= :id")
    suspend fun getByIdSeries(id:String): SeriesShortSpecs

    @Update
    suspend fun updateSeries(series: List<SeriesShortSpecs>)

    @Insert
    suspend fun insertSeries(series: SeriesShortSpecs)

    @Delete
    suspend fun deleteSeries(series: SeriesShortSpecs)

}