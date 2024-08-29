package com.ardidong.omdbapp.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.ardidong.omdbapp.domain.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

class HomeViewModel : ViewModel() {
    private var _state = MutableStateFlow(HomeScreenState.EMPTY)
    val state: StateFlow<HomeScreenState> get() = _state
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
