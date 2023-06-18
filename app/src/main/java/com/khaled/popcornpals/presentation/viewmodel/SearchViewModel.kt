package com.khaled.popcornpals.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.popcornpals.domain.model.Movie
import com.khaled.popcornpals.domain.usecase.movie_use_case.MovieUseCases
import com.khaled.popcornpals.util.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases
) : ViewModel() {

    private val _status = MutableLiveData<NetworkStatus>()

    val status: LiveData<NetworkStatus>
        get() = _status

    private val _searchedMovies = MutableLiveData<List<Movie>>()

    val searchedMovies: LiveData<List<Movie>>
        get() = _searchedMovies

    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()

    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    fun getSearchedMovies(expression: String) {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                _searchedMovies.value = movieUseCases.searchUseCase(expression)
                _status.value = NetworkStatus.DONE
                Log.e("TAG", _searchedMovies.value.toString())
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                Log.e("TAG", e.message.toString())
            }
        }
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