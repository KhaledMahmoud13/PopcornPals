package com.khaled.popcornpals.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.popcornpals.domain.model.Category
import com.khaled.popcornpals.domain.model.Movie
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetComingSoonMoviesUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetInTheatersMoviesUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetMostPopularMoviesUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetMovieDetailsUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetTopMoviesUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.MovieUseCases
import com.khaled.popcornpals.util.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases
) : ViewModel() {

    private val _status = MutableLiveData<NetworkStatus>()

    val status: LiveData<NetworkStatus>
        get() = _status

    private val _movieCategories = MutableLiveData<List<Category>>()
    val movieCategories: LiveData<List<Category>> get() = _movieCategories

    private val _popularMovies = MutableLiveData<List<Movie>>()

    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _topMovies = MutableLiveData<List<Movie>>()

    val topMovies: LiveData<List<Movie>>
        get() = _topMovies

    private val _inTheatersMovies = MutableLiveData<List<Movie>>()

    val inTheatersMovies: LiveData<List<Movie>>
        get() = _inTheatersMovies

    private val _comingSoonMovies = MutableLiveData<List<Movie>>()

    val comingSoonMovies: LiveData<List<Movie>>
        get() = _comingSoonMovies

    private val _mostPopularMovies = MutableLiveData<List<Movie>>()

    val mostPopularMovie: LiveData<List<Movie>>
        get() = _mostPopularMovies

    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()

    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    init {
        extractedMovieCategory()
    }

    fun extractedMovieCategory() {
        _popularMovies.observeForever { movies ->
            if (movies.isNotEmpty()) {
                moviesCategories()
            }
        }
        _topMovies.observeForever { movies ->
            if (movies.isNotEmpty()) {
                moviesCategories()
            }
        }
        _inTheatersMovies.observeForever { movies ->
            if (movies.isNotEmpty()) {
                moviesCategories()
            }
        }
        _comingSoonMovies.observeForever { movies ->
            if (movies.isNotEmpty()) {
                moviesCategories()
            }
        }
    }

    fun getAllMovies() {
        GlobalScope.launch {
            _status.postValue(NetworkStatus.LOADING)
            try {
                if (_status.value != NetworkStatus.DONE) {
                    _popularMovies.postValue(movieUseCases.getMostPopularMoviesUseCase())
                    _topMovies.postValue(movieUseCases.getTopMoviesUseCase())
                    _inTheatersMovies.postValue(movieUseCases.getInTheatersMoviesUseCase())
                    _comingSoonMovies.postValue(movieUseCases.getComingSoonMoviesUseCase())
                    _mostPopularMovies.postValue(_inTheatersMovies.value!!.take(5))
                    _status.postValue(NetworkStatus.DONE)
                    Log.e("TAG", _inTheatersMovies.value.toString())
                    Log.e("TAG", _status.value.toString())
                }
            } catch (e: Exception) {
                _status.postValue(NetworkStatus.ERROR)
                _popularMovies.postValue(mutableListOf())
                Log.e("TAG", e.message.toString())
            }
        }
    }

    private fun moviesCategories() {
        val categories = mutableListOf<Category>()
        categories.add(Category(1, "Most Popular Movies", _popularMovies.value ?: emptyList()))
        categories.add(Category(2, "Top Movies", _topMovies.value ?: emptyList()))
        categories.add(Category(3, "In ThTheaters Movies", _inTheatersMovies.value ?: emptyList()))
        categories.add(Category(4, "Coming Soon Movies", _comingSoonMovies.value ?: emptyList()))
        _movieCategories.value = categories.toMutableList()
    }

    fun displayMovieDetails(id: String) {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                _navigateToSelectedMovie.value = movieUseCases.getMovieDetailsUseCase(id)
//                Log.d("TAG", _navigateToSelectedMovie.value.toString())
                _status.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                Log.e("TAG", e.message.toString())
            }
        }
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

}