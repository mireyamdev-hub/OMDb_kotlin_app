package com.example.omdbmoviesapp.ui.fragments

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbmoviesapp.data.APIService
//import com.example.omdbmoviesapp.data.MovieShortSpecs
import com.example.omdbmoviesapp.data.MoviesResponse
import com.example.omdbmoviesapp.data.SeriesResponse
import com.example.omdbmoviesapp.data.SeriesShortSpecs
import com.example.omdbmoviesapp.databinding.FragmentSeriesBinding
import com.example.omdbmoviesapp.db.MoviesSeriesDb
import com.example.omdbmoviesapp.ui.adapters.MoviesAdapter
import com.example.omdbmoviesapp.ui.adapters.MoviesFavsAdapter
import com.example.omdbmoviesapp.ui.adapters.SeriesAdapter
import com.example.omdbmoviesapp.ui.adapters.SeriesFavsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SeriesFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private var _binding: FragmentSeriesBinding? = null
    private val binding get() = _binding!!
    //Adapter
    private lateinit var adapter: SeriesAdapter
    private val seriesImages = mutableListOf<SeriesShortSpecs>()
    //Adapter series favs
    private lateinit var adapterseriesfavs: SeriesFavsAdapter
    private var seriesfavsImages = mutableListOf<SeriesShortSpecs>()
    private var appContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appContext = getActivity()?.getApplicationContext()
        binding.searchSeries.setOnQueryTextListener(this)
        binding.swipeRefreshLayout.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.updateSeries(seriesfavsImages)
                seriesfavsImages = MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinListSeries()
                        as MutableList<SeriesShortSpecs>
            }
            binding.swipeRefreshLayout.isRefreshing = false
            adapterseriesfavs.notifyDataSetChanged()
            Log.d(ContentValues.TAG, "SWIPE DONE")
            initSeriesfavRecyclerView()
        }
        initSeriesRecyclerView()
        initSeriesfavRecyclerView()
    }

    private fun initSeriesfavRecyclerView() {
        favSeries()
        adapterseriesfavs = SeriesFavsAdapter(seriesfavsImages, seriesfavsImages,appContext)
        var linearLayoutManager = LinearLayoutManager(context)
        binding.rvSeriesFavs.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.stackFromEnd = true
        linearLayoutManager.reverseLayout = true
        binding.rvSeriesFavs.adapter = adapterseriesfavs
    }

    private fun initSeriesRecyclerView() {
        adapter = SeriesAdapter(seriesImages, seriesfavsImages, this.appContext)
        binding.rvSeriesAll.layoutManager = GridLayoutManager(context,2)
        binding.rvSeriesAll.adapter = adapter
    }

    private fun favSeries() {
        seriesfavsImages = MoviesSeriesDb.getAppDatabase(context)?.movieDao()?.getAllinListSeries()
                as MutableList<SeriesShortSpecs>
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByTitle(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            //Secondary thread
            val call: Response<SeriesResponse> = getRetrofit().create(APIService::class.java)
                .getSeriesByTitle("?apikey=(your apikey here)&s=$query&type=series")
            Log.v(ContentValues.TAG, "RESPONSE ---> " + call);
            val series: SeriesResponse? = call.body()
            //Main thread
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    //show recyclerview
                    val posters = series?.shortmoviedescription ?: emptyList()
                    seriesImages.clear()
                    seriesImages.addAll(posters)
                    adapter.notifyDataSetChanged()
                }
                else{
                    showError(call.message())
                    showError(series.toString())
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    //Búsqueda texto
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByTitle(query.toLowerCase())
            binding.searchSeries.clearFocus()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}
