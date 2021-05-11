package com.example.omdbmoviesapp.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbmoviesapp.data.APIService
import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.data.MoviesResponse
import com.example.omdbmoviesapp.databinding.FragmentMoviesBinding
import com.example.omdbmoviesapp.db.MoviesSeriesDb
import com.example.omdbmoviesapp.ui.adapters.MoviesAdapter
import com.example.omdbmoviesapp.ui.adapters.MoviesFavsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.GridLayoutManager
import java.util.TimerTask

import java.util.Timer

class MoviesFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private var _binding:FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    //Adapter
    private lateinit var adapter:MoviesAdapter
    private val moviesImages = mutableListOf<MovieShortSpecs>()
    //Adapter favs
    private lateinit var adapterfavs:MoviesFavsAdapter
    private var moviesfavsImages = mutableListOf<MovieShortSpecs>()
    private var appContext:Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appContext = getActivity()?.getApplicationContext()
        //Search movies listener
        binding.searchMovies.setOnQueryTextListener(this)
        //swipte favourites update
        binding.swipeRefreshLayout.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.update(moviesfavsImages)
                moviesfavsImages = MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinList()
                        as MutableList<MovieShortSpecs>
            }
            binding.swipeRefreshLayout.isRefreshing = false
            adapterfavs.notifyDataSetChanged()
            Log.d(TAG, "SWIPE DONE")
            initfavRecyclerView()
        }
        //Recyclers
        initRecyclerView()
        initfavRecyclerView()
    }

    private fun initfavRecyclerView() {
        favMovies()
        adapterfavs = MoviesFavsAdapter(moviesfavsImages, moviesfavsImages, appContext)
        var linearLayoutManager = LinearLayoutManager(context)
        binding.rvMoviesFavs.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.stackFromEnd = true
        linearLayoutManager.reverseLayout = true
        binding.rvMoviesFavs.adapter = adapterfavs
        adapterfavs.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        adapter = MoviesAdapter(moviesImages, moviesfavsImages, this.appContext)
        binding.rvMoviesAll.layoutManager = GridLayoutManager(context, 2)
        binding.rvMoviesAll.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun favMovies() {
        moviesfavsImages = MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinList()
                as MutableList<MovieShortSpecs>
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByTitle(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            //Secondary
            val call:Response<MoviesResponse> = getRetrofit().create(APIService::class.java)
                .getMovieByTitle("?apikey=(your apikey here)&s=$query&type=movie")
            Log.v(TAG, "RESPONSE ---> " + call);
            val movies:MoviesResponse? = call.body()
            //Main thread
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    //show recyclerview
                    val posters = movies?.shortmoviedescription ?: emptyList()
                    moviesImages.clear()
                    moviesImages.addAll(posters)
                    adapter.notifyDataSetChanged()
                }
                else{
                    showError(call.message())
                    showError(movies.toString())
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    //Search movie by text
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByTitle(query.toLowerCase())
            //Hide keyboard
            binding.searchMovies.clearFocus()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}
