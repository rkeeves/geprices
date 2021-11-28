package com.github.rkeeves.geprices.data.local

import androidx.room.ColumnInfo
import com.github.rkeeves.geprices.data.remote.CommodityDto
import java.sql.Timestamp

class CommodityDetailsUpdate(
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "last_update")
    val lastDetailsUpdateAt: Timestamp,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "members_only")
    val membersOnly: Boolean,
    @ColumnInfo(name = "current_price")
    val currentPrice: Int,
    @ColumnInfo(name = "today_price")
    val todayPriceChange: Int,
    @ColumnInfo(name = "short_term_change")
    val shortTermChange: Double,
    @ColumnInfo(name = "mid_term_change")
    val midTermChange: Double,
    @ColumnInfo(name = "long_term_change")
    val longTermChange: Double,
) {
    companion object {
        fun of(dto: CommodityDto): CommodityDetailsUpdate {
            return CommodityDetailsUpdate(
                id = dto.id,
                name = dto.name,
                lastDetailsUpdateAt =  Timestamp(System.currentTimeMillis()),
                type = dto.type,
                description = dto.description,
                membersOnly = dto.members,
                currentPrice = parsePrice(dto.current.price),
                todayPriceChange = parsePrice(dto.today.price),
                shortTermChange = parseChangePercent(dto.day30.change),
                midTermChange = parseChangePercent(dto.day90.change),
                longTermChange = parseChangePercent(dto.day180.change),
            )
        }

        private fun parsePrice(price: String): Int {
            val sanitizedPrice = price.replace(" ","").replace(",", "")
            return when {
                price.endsWith("m") -> {
                    (sanitizedPrice.substring(0, sanitizedPrice.length-1).toDouble() * 1000000.0).toInt()
                }
                price.endsWith("k") -> {
                    (sanitizedPrice.substring(0, sanitizedPrice.length-1).toDouble() * 1000.0).toInt()
                }
                else -> {
                    sanitizedPrice.toDouble().toInt()
                }
            }
        }

        private fun parseChangePercent(changePercent: String): Double {
            return when {
                changePercent.endsWith("%") -> {
                    changePercent.substring(0, changePercent.length-1).toDouble()
                } else -> {
                    changePercent.toDouble()
                }
            }
        }
    }
}