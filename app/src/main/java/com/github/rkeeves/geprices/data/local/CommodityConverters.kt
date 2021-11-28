package com.github.rkeeves.geprices.data.local

import androidx.room.TypeConverter
import java.sql.Timestamp

class CommodityConverters {

    @TypeConverter
    fun longToTimestamp(timestampAsLong: Long?): Timestamp? {
        return Companion.longToTimestamp(timestampAsLong)
    }

    @TypeConverter
    fun timestampToLong(timestamp: Timestamp?): Long? {
        return Companion.timestampToLong(timestamp)
    }


    companion object {
        fun longToTimestamp(timestampAsLong: Long?): Timestamp? {
            return timestampAsLong?.let { Timestamp(it) }
        }

        fun timestampToLong(timestamp: Timestamp?): Long? {
            return timestamp?.time
        }
    }
}