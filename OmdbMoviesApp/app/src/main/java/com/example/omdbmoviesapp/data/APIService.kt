package com.example.omdbmoviesapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getMovieByTitle(@Url url: String):Response<MoviesResponse>
    @GET
    suspend fun getSeriesByTitle(@Url url: String):Response<SeriesResponse>
}