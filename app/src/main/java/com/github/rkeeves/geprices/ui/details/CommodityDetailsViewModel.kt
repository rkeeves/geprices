package com.github.rkeeves.geprices.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rkeeves.geprices.data.local.Commodity
import com.github.rkeeves.geprices.repo.CommodityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommodityDetailsViewModel(
    private val commodityId: Int = 0,
    private val commodityRepository: CommodityRepository
) : ViewModel()  {
    private val _commodity = MutableLiveData<Commodity>(commodityRepository.findById(commodityId).value)
    private val commodity: LiveData<Commodity> = commodityRepository.findById(commodityId)

    fun getCommodity() = commodity

    private val _navigateToList = MutableLiveData<Boolean?>()

    val navigateToList: LiveData<Boolean?>
    get() = _navigateToList

    fun doneNavigating() {
        _navigateToList.value = null
    }

    fun onClose() {
        _navigateToList.value = true
    }

    fun onSync() {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedCommodity = commodityRepository.syncAndFindById(commodityId)
            _commodity.postValue(updatedCommodity.value)
        }
    }
}