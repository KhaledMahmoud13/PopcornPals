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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException

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
                _movies.value = parseMovie(JSONObject(Api.retrofitService.getMostPopularMovies()))
                _mostPopularMovie.value = (_movies.value as ArrayList<Movie>)[0]
                (_movies.value as ArrayList<Movie>).removeAt(0)
                Log.e("TAG", _movies.value.toString())
                _status.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                _movies.value = ArrayList()
                Log.e("TAG", e.message.toString())
            }
        }
    }

    fun filter(
        topMovies: Boolean,
        inTheatersMovies: Boolean,
        comingSoonMovies: Boolean,
        boxOfficeMovies: Boolean,
        boxOfficeAllTimeMovies: Boolean
    ) {
        viewModelScope.launch {
            if (topMovies) {
                _movies.value = parseMovie(JSONObject(Api.retrofitService.getTopMovies()))
            } else if (inTheatersMovies) {
                _movies.value = parseMovie(JSONObject(Api.retrofitService.getInTheatersMovies()))
            } else if (comingSoonMovies) {
                _movies.value = parseMovie(JSONObject(Api.retrofitService.getComingSoonMovies()))
            } else if (boxOfficeMovies) {
                _movies.value = parseMovie(JSONObject(Api.retrofitService.getBoxOfficeMovies()))
            } else if (boxOfficeAllTimeMovies) {
                _movies.value =
                    parseMovie(JSONObject(Api.retrofitService.getBoxOfficeAllTimeMovies()))
            } else {
                _movies.value = parseMovie(JSONObject(Api.retrofitService.getMostPopularMovies()))
            }
        }
    }

}