package com.khaled.popcornpals.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.popcornpals.model.Movie
import com.khaled.popcornpals.network.Api
import com.khaled.popcornpals.network.parseMovie
import com.khaled.popcornpals.util.NetworkStatus
import kotlinx.coroutines.launch
import org.json.JSONObject

class MovieViewModel : ViewModel() {

    private val _status = MutableLiveData<NetworkStatus>()

    val status: LiveData<NetworkStatus>
        get() = _status

    private val _movies = MutableLiveData<MutableList<Movie>>()

    val movies: LiveData<MutableList<Movie>>
        get() = _movies

    private val _mostPopularMovie = MutableLiveData<Movie>()

    val mostPopularMovie: LiveData<Movie>
        get() = _mostPopularMovie

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                val listOfMovies = Api.retrofitService.getMostPopularMovies()
                _movies.value = parseMovie(JSONObject(listOfMovies))
                _mostPopularMovie.value = (_movies.value as ArrayList<Movie>)[0]
                Log.e("TAG", _movies.value.toString())
                _status.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                _movies.value = ArrayList()
                Log.e("TAG", e.message.toString())
            }
        }
    }
}