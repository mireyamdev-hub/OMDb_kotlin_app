package com.example.omdbmoviesapp.data

import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("Response") var response: String,
    @SerializedName("Search") var shortmoviedescription: List<MovieShortSpecs>) {
}

data class SeriesResponse (
    @SerializedName("Response") var response: String,
    @SerializedName("Search") var shortmoviedescription: List<SeriesShortSpecs>) {
}