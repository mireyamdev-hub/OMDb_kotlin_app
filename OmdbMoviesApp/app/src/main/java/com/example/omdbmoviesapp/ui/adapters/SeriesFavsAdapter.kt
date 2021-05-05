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
import com.example.omdbmoviesapp.db.MoviesSeriesDb
import com.example.omdbmoviesapp.ui.viewholders.SeriesFavsViewHolder

class SeriesFavsAdapter (var seriesfavs: List<SeriesShortSpecs>,
                         var seriesfavsImages: List<SeriesShortSpecs>,
                         private val appContext: Context?):
    RecyclerView.Adapter<SeriesFavsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesFavsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SeriesFavsViewHolder(layoutInflater.inflate(R.layout.item_series_favs , parent, false))
    }

    override fun onBindViewHolder(holder: SeriesFavsViewHolder, position: Int) {
        val item: SeriesShortSpecs = seriesfavs[position]
        if (appContext != null) {
            holder.show(item,seriesfavs, appContext)
            holder.deleteSeriesfav(item,seriesfavsImages,appContext);
        }
        else{
            //Log.d
        }
    }

    override fun getItemCount(): Int = seriesfavs.size

}