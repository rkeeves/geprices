package com.github.rkeeves.geprices.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.github.rkeeves.geprices.R
import com.github.rkeeves.geprices.data.local.*
import com.github.rkeeves.geprices.data.remote.CommodityApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.sql.Timestamp
import kotlin.collections.List

class DefaultCommodityRepository(
    private val app: Context,
    private val commodityDao: CommodityDao,
    private val commodityApi: CommodityApi
): CommodityRepository{
    override fun findById(commodityId: Int): LiveData<Commodity> {
        return commodityDao.findById(commodityId)
    }

    override fun findAll(): LiveData<List<Commodity>> {
        return commodityDao.listAll()
    }

    override suspend fun findMaxTenByNameStartingWith(prefix: String): List<Commodity> {
        return commodityDao.findMaxTenByNameStartingWith(prefix)
    }

    override suspend fun syncAndFindById(commodityId: Int): LiveData<Commodity> {
        val commodityDtoResponse = commodityApi.getItemInfo(commodityId)
        if (commodityDtoResponse.isSuccessful) {
            commodityDtoResponse.body()?.let { body ->
                val commodityDto = body.item
                commodityDao.update(CommodityDetailsUpdate.of(commodityDto))
            }
        }
        return commodityDao.findById(commodityId)
    }

    override suspend fun preload() {
        val count = commodityDao.count()
        if (count < 1) {
            app.resources.openRawResource(R.raw.commodity_names)
                .use { inputStream -> parseCommodityNames(inputStream) }
                .map { commodityName -> commodityOf(commodityName.id, commodityName.name) }
                .let { commodityEntities ->  commodityDao.insertAll(commodityEntities) }
        }
    }

    private fun parseCommodityNames(inputStream: InputStream): List<CommodityName> {
        val jsonString = inputStream
            .bufferedReader()
            .use { it.readText() }
        val typeToken = object : TypeToken<List<CommodityName>>() {}.type
        return Gson().fromJson(jsonString, typeToken)
    }

    private fun commodityOf(id: Int, name: String): Commodity {
        return Commodity(
            id = id,
            name = name,
            lastDetailsUpdateAt = Timestamp(0),
            type = "",
            description = "",
            membersOnly = false,
            currentPrice = 0,
            todayPriceChange = 0,
            shortTermChange = 0.0,
            midTermChange = 0.0,
            longTermChange = 0.0,
        )
    }
}