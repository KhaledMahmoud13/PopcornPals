package com.khaled.popcornpals.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.popcornpals.data.model.Movie
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

    fun getSearchedMovies(expression: String) {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                _searchedMovies.value = movieUseCases.searchUseCase(expression)
                _status.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                Log.e("TAG", e.message.toString())
            }
        }
    }
}