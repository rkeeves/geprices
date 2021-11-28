package com.github.rkeeves.geprices.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "commodity_entity")
data class Commodity(
    @PrimaryKey
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
)