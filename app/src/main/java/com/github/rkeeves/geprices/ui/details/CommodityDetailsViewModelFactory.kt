package com.github.rkeeves.geprices.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.rkeeves.geprices.repo.CommodityRepository

class CommodityDetailsViewModelFactory(
    private val commodityId: Int,
    private val commodityRepository: CommodityRepository) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommodityDetailsViewModel::class.java)) {
            return CommodityDetailsViewModel(commodityId, commodityRepository) as T
        }
        throw IllegalArgumentException("Welcome to the Android static types course")
    }
}