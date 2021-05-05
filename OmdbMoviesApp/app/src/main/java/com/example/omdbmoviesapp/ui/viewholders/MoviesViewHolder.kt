package com.example.omdbmoviesapp.ui.viewholders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbmoviesapp.R
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.databinding.ItemMoviesBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.omdbmoviesapp.db.MoviesSeriesDb


class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMoviesBinding.bind(view)
    private var appContext: Context? = null

    fun bind(movieobject: MovieShortSpecs) {
        binding.movieTitle.setText(movieobject.title)
        binding.movieYear.setText(movieobject.year)
        Picasso.get().load(movieobject.posterurl).into(binding.ivMovies)
    }

    fun fav(movieobject: MovieShortSpecs, moviefavobject: List<MovieShortSpecs>, context: Context) {
        binding.btnFav.setOnClickListener {
            binding.btnFav.setBackgroundResource(R.drawable.btn_fav_filled)
            appContext = context
            CoroutineScope(Dispatchers.IO).launch {
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.insert(movieobject)
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.update(moviefavobject)
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinList()
            }
        }
    }
}