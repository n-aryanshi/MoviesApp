package com.example.moviesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.repository.ShowsRepo
import com.example.moviesapp.view.Shows
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.moviesapp.util.Result

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val repository: ShowsRepo
) : ViewModel() {

    private val _showsState = MutableStateFlow<Result<List<Shows>>>(Result.Idle)
    val showsState: StateFlow<Result<List<Shows>>> = _showsState

    private val _currentTab = MutableStateFlow("movie")
    val currentTab: StateFlow<String> get() = _currentTab

    fun fetchShows(type: String? = null) {
        val typesToFetch = type ?: _currentTab.value
        _currentTab.value = typesToFetch

        viewModelScope.launch {
            _showsState.value = Result.Loading
            val result = repository.getShows(typesToFetch)
            _showsState.value = result
        }
    }

    fun getShowById(movieId: String): Shows? {
        // If showsState has a success value, search it
        val current = _showsState.value
        return if (current is Result.Success) {
            current.data.find { it.id.toString() == movieId } // adapt to id type
        } else {
            null
        }
    }
}