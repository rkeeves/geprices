package com.github.rkeeves.geprices.data.remote

data class CommodityDto(
    val id: Int,
    val name: String,
    val description: String,
    val members: Boolean,
    val type: String,
    val typeIcon: String,
    val icon: String,
    val icon_large: String,
    val current: CommodityPriceDto,
    val today: CommodityPriceDto,
    val day30: CommodityPriceHistoryDto,
    val day90: CommodityPriceHistoryDto,
    val day180: CommodityPriceHistoryDto,
)