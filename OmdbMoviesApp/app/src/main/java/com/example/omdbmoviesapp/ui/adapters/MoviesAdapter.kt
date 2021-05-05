package com.example.omdbmoviesapp.ui.adapters

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbmoviesapp.R
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.db.MoviesSeriesDb
import com.example.omdbmoviesapp.ui.viewholders.MoviesViewHolder

class MoviesAdapter(val movies: List<MovieShortSpecs>,
                    var moviesfavsImages: List<MovieShortSpecs>,
                    private val appContext: Context?):RecyclerView.Adapter<MoviesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.item_movies , parent, false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
       val item: MovieShortSpecs = movies[position]
        if (appContext != null) {
            holder.bind(item)
            holder.fav(item, moviesfavsImages, appContext)
        }
        else{
            //Log.d
        }
    }

    override fun getItemCount(): Int = movies.size
}