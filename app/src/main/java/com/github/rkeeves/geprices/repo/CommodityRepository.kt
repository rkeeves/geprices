package com.github.rkeeves.geprices.repo

import androidx.lifecycle.LiveData
import com.github.rkeeves.geprices.data.local.Commodity

interface CommodityRepository {
    fun findById(commodityId: Int): LiveData<Commodity>
    fun findAll():  LiveData<List<Commodity>>
    suspend fun findMaxTenByNameStartingWith(prefix: String): List<Commodity>
    suspend fun syncAndFindById(commodityId: Int): LiveData<Commodity>
    suspend fun preload()
}