package com.github.rkeeves.geprices.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CommodityDao {

    @Query("SELECT COUNT(*) FROM commodity_entity LIMIT 1" )
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(commodityEntities: List<Commodity>)

    @Query("SELECT * FROM commodity_entity WHERE id = :id" )
    fun findById(id: Int): LiveData<Commodity>

    @Update(entity = Commodity::class)
    suspend fun update(commodityDetailsUpdate: CommodityDetailsUpdate)

    @Query("SELECT * FROM commodity_entity WHERE name = :substring ORDER BY name" )
    fun findAllByNameStartingWith(substring: String): LiveData<List<Commodity>>

    @Query("SELECT * FROM commodity_entity ORDER BY name" )
    fun listAll(): LiveData<List<Commodity>>

    @Query("SELECT * FROM commodity_entity WHERE LOWER(name) LIKE LOWER(:prefix || '%') ORDER BY name LIMIT 10")
    suspend fun findMaxTenByNameStartingWith(prefix: String): List<Commodity>
}