package com.github.rkeeves.geprices.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.rkeeves.geprices.repo.CommodityRepository
import com.github.rkeeves.geprices.ui.details.CommodityDetailsViewModel

class CommodityListViewModelProviderFactory(
    private val commodityRepository: CommodityRepository) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommodityListViewModel::class.java)) {
            return CommodityListViewModel(commodityRepository) as T
        }
        throw IllegalArgumentException("Totally safe")
    }
}