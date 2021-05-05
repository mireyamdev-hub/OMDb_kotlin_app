package com.example.omdbmoviesapp.ui.adapters

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbmoviesapp.R
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.db.MoviesSeriesDb
import com.example.omdbmoviesapp.ui.viewholders.MoviesFavsViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesFavsAdapter(var moviesfavs: List<MovieShortSpecs>,
                        var moviesfavsImages: List<MovieShortSpecs>,
                        private val appContext: Context?) :
    RecyclerView.Adapter<MoviesFavsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesFavsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesFavsViewHolder(
            layoutInflater.inflate(
                R.layout.item_movies_favs,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesFavsViewHolder, position: Int) {
        val item: MovieShortSpecs = moviesfavs[position]
        if (appContext != null) {
            holder.show(item)
            holder.deletefav(item,moviesfavsImages,appContext)
        } else {
            //Log.d
        }
    }

    override fun getItemCount(): Int = moviesfavs.size

}
