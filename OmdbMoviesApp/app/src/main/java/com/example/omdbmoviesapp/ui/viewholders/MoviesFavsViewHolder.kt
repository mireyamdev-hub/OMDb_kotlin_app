package com.example.omdbmoviesapp.ui.viewholders

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbmoviesapp.R
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.databinding.ItemMoviesBinding
import com.example.omdbmoviesapp.databinding.ItemMoviesFavsBinding
import com.example.omdbmoviesapp.db.MoviesSeriesDb
import com.example.omdbmoviesapp.ui.fragments.MoviesFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesFavsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemMoviesFavsBinding.bind(view)
    private var appContext: Context? = null

    fun show(movieobject: MovieShortSpecs){
        binding.moviefavTitle.setText(movieobject.title)
        binding.moviefavYear.setText(movieobject.year)
        Picasso.get().load(movieobject.posterurl).into(binding.ivfavMovies)
    }

    fun deletefav(movieobject: MovieShortSpecs, moviefavobject: List<MovieShortSpecs>,context: Context){
        binding.btnDelete.setOnClickListener {
            appContext = context
            binding.btnDelete.setBackgroundResource(R.drawable.btn_delete_filled)
            CoroutineScope(Dispatchers.IO).launch {
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.delete(movieobject)
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.update(moviefavobject)
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinList()
            }

        }
    }
}