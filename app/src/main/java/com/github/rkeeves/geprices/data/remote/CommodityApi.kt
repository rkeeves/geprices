package com.github.rkeeves.geprices.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CommodityApi {
    @GET("/m=itemdb_oldschool/api/catalogue/detail.json")
    suspend fun getItemInfo(@Query("item") itemId: Int): Response<CommodityResponseDto>
}