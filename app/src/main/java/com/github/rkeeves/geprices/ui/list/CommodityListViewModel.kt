package com.github.rkeeves.geprices.ui.list

import androidx.lifecycle.*
import com.github.rkeeves.geprices.data.local.Commodity
import com.github.rkeeves.geprices.repo.CommodityRepository
import kotlinx.coroutines.*

class CommodityListViewModel(
    private val commodityRepository: CommodityRepository
): ViewModel() {


    private val _commodities = MutableLiveData<List<Commodity>>()
    val commodities: LiveData<List<Commodity>>
        get() = _commodities


    private var _showSnackbarEvent = MutableLiveData<Boolean?>()
    val showSnackBarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    private val _navigateToSleepDetail = MutableLiveData<Int>()
    val navigateToSleepDetail
        get() = _navigateToSleepDetail

    fun onSleepNightClicked(id: Int) {
        _navigateToSleepDetail.value = id
    }

    fun onSleepDetailNavigated() {
        _navigateToSleepDetail.value = null
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            commodityRepository.preload()
        }
    }

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _showSnackbarEvent.value = true
            delay(500L)
            val result = commodityRepository.findMaxTenByNameStartingWith(query)
            _commodities.value = result
        }
    }
}