package com.ardidong.omdbapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ardidong.omdbapp.domain.Media
import com.ardidong.omdbapp.domain.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {
    private var _state = MutableStateFlow(HomeScreenState.EMPTY)
    val state: StateFlow<HomeScreenState> get() = _state

    fun search(title: String, page: Int = 1) {
        viewModelScope.launch {
            repository.searchMedia("inside out", 1).fold(
                success = {
                    Log.d("HOME VIEW MODEL", it.results.toString())
                },
                failure = {
                    Log.e("HOME VIEW MODEL", it.message)
                }
            )
        }
    }
}

data class HomeScreenState(
    val mediaList: Flow<PagingData<Media>>,
    val titleFilter: String
){
    companion object {
        val EMPTY = HomeScreenState(
            mediaList = emptyFlow(),
            titleFilter = ""
        )
    }
}
