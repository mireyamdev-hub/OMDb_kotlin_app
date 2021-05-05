package com.example.omdbmoviesapp.ui.viewholders

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbmoviesapp.R
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.data.SeriesShortSpecs
import com.example.omdbmoviesapp.databinding.ItemMoviesBinding
import com.example.omdbmoviesapp.databinding.ItemSeriesBinding
import com.example.omdbmoviesapp.db.MoviesSeriesDb
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSeriesBinding.bind(view)
    private var appContext: Context? = null

    fun bind(movieobject: SeriesShortSpecs) {
        binding.seriesTitle.setText(movieobject.title)
        binding.seriesYear.setText(movieobject.year)
        Picasso.get().load(movieobject.posterurl).into(binding.ivSeries)
    }

    fun fav(movieobject: SeriesShortSpecs, moviefavobject: List<SeriesShortSpecs>, context: Context) {
        binding.btnFav.setOnClickListener {
            binding.btnFav.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            appContext = context
            CoroutineScope(Dispatchers.IO).launch {
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.insertSeries(movieobject)
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.updateSeries(moviefavobject)
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinListSeries()
            }
        }
    }
}