package com.example.omdbmoviesapp.ui.adapters

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbmoviesapp.R
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.data.SeriesShortSpecs
import com.example.omdbmoviesapp.ui.viewholders.SeriesViewHolder

class SeriesAdapter (val series: List<SeriesShortSpecs>,
                     var seriesfavsImages: List<SeriesShortSpecs>,
                     private val appContext: Context?): RecyclerView.Adapter<SeriesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SeriesViewHolder (layoutInflater.inflate(R.layout.item_series , parent, false))
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val item: SeriesShortSpecs = series[position]
        holder.bind(item)
        if (appContext != null) {
            holder.fav(item, seriesfavsImages, appContext)
        }
        else{
            //Log.d
        }
    }

    override fun getItemCount(): Int = series.size
}