package com.example.flowrspot.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowrspot.domain.model.FlowerModel
import com.example.flowrspot.domain.use_case.FlowrSpotUseCases
import com.example.flowrspot.utility.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val flowrSpotUseCases: FlowrSpotUseCases
): ViewModel() {

    /**
     * It's like a state flow, which will tell us the right action
     * that needs to be applied in the UI.
     */
    private val _state = MutableSharedFlow<HomeState>()
    val state = _state.asSharedFlow()

    /**
     * This list will be bound to recycler view adapter.
     * Can hold the search results, or the retrieved available flowers.
     */
    val mainList = arrayListOf<FlowerModel>()

    /**
     * This list will contain all the available flowers that are retrieved by the server,
     * including the result from the pagination implementation.
     * We will keep this reference to skip the need of retrieving these results from server again.
     */
    private val availableFlowers = arrayListOf<FlowerModel>()

    /**
     * If user has searched, this flag will prevent the pagination from executing.
     */
    var userHasSearched: Boolean = false

    /**
     * This flag will block the pagination if set to true.
     * No more available flowers can be retrieved.
     */
    var noMorePagination: Boolean = false

    /**
     * Everytime the retrieveFlowers() is executed, the variable will
     * be incremented to retrieve the available flowers from other pages as well.
     */
    private var page: Int = 0

    /**
     * Retrieve the list of available flowers.
     */
    fun retrieveFlowers() {
        page++
        viewModelScope.launch {
            flowrSpotUseCases.retrieveFlowersUseCase(page).collect {
                when(it) {
                    is Resource.Loading<*> -> _state.emit(HomeState.ShowProgressBar)
                    is Resource.Error<*> -> _state.emit(HomeState.ToastMessage(it.message ?: "Something went wrong!"))
                    is Resource.Success<*> -> {
                        if (it.data!!.isEmpty()) {
                            noMorePagination = true
                            return@collect
                        }

                        availableFlowers.addAll(it.data)
                        mainList.addAll(it.data)
                        _state.emit(HomeState.UpdateFlowersView)
                    }
                }
            }
        }
    }

    /**
     * Search for a flower by name or latin_name.
     */
    fun searchFlowers(value: String) {
        if (value.isEmpty()) {
            viewModelScope.launch { _state.emit(HomeState.ToastMessage("Search field is empty!!!")) }
            return
        }

        viewModelScope.launch {
            flowrSpotUseCases.searchFlowersUseCase(value).collect {
                when(it) {
                    is Resource.Loading<*> -> _state.emit(HomeState.ShowProgressBar)
                    is Resource.Error<*> -> _state.emit(HomeState.ToastMessage(it.message ?: "Something went wrong!"))
                    is Resource.Success<*> -> {
                        userHasSearched = true
                        //
                        mainList.clear()
                        mainList.addAll(it.data!!)
                        _state.emit(HomeState.UpdateFlowersView)
                    }
                }
            }
        }
    }

    /**
     * This method will show the available flowers again, after a search action is dismissed.
     */
    fun showTheAvailableFlowers() {
        userHasSearched = false
        //
        mainList.clear()
        mainList.addAll(availableFlowers)
        viewModelScope.launch {
            _state.emit(HomeState.UpdateFlowersView)
        }
    }
}

sealed class HomeState {
    object ShowProgressBar: HomeState()
    object UpdateFlowersView: HomeState()
    data class ToastMessage(val message: String): HomeState()
}