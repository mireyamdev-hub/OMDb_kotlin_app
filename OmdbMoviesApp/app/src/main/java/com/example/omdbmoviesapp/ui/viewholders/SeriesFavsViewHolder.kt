package com.example.omdbmoviesapp.ui.viewholders

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbmoviesapp.R
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.data.SeriesShortSpecs
import com.example.omdbmoviesapp.databinding.ItemMoviesFavsBinding
import com.example.omdbmoviesapp.databinding.ItemSeriesFavsBinding
import com.example.omdbmoviesapp.db.MoviesSeriesDb
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeriesFavsViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemSeriesFavsBinding.bind(view)
    private var appContext: Context? = null

    fun show(movieobject: SeriesShortSpecs,
        moviefavobject: List<SeriesShortSpecs>,
        context: Context)
    {
        binding.seriesfavTitle.setText(movieobject.title)
        binding.seriesfavYear.setText(movieobject.year)
        Picasso.get().load(movieobject.posterurl).into(binding.ivfavSeries)
        CoroutineScope(Dispatchers.IO).launch {
            MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.updateSeries(moviefavobject)
            MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinListSeries()
        }
    }

    fun deleteSeriesfav(seriesobject: SeriesShortSpecs, seriesfavobject: List<SeriesShortSpecs>,
                        context: Context){
        binding.btnDelete.setOnClickListener {
            appContext = context
            binding.btnDelete.setBackgroundResource(R.drawable.btn_delete_filled)
            CoroutineScope(Dispatchers.IO).launch {
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.deleteSeries(seriesobject)
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.updateSeries(seriesfavobject)
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinListSeries()
            }
        }
    }
}