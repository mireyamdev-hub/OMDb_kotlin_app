package com.example.omdbmoviesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movieinfo")
data class MovieShortSpecs(
    @SerializedName("Title") var title: String,
    @SerializedName("Year") var year: String,
    @SerializedName("imdbID") @PrimaryKey var id: String,
    @SerializedName("Type") var type: String,
    @SerializedName("Poster") var posterurl: String
)
@Entity(tableName = "seriesinfo")
data class SeriesShortSpecs(
    @SerializedName("Title") var title: String,
    @SerializedName("Year") var year: String,
    @SerializedName("imdbID") @PrimaryKey var id: String,
    @SerializedName("Type") var type: String,
    @SerializedName("Poster") var posterurl: String
){
}



