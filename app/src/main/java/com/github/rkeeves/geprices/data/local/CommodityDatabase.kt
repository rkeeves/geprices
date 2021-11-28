package com.github.rkeeves.geprices.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Commodity::class,
    ],
    version=1)
@TypeConverters(CommodityConverters::class)
abstract class CommodityDatabase : RoomDatabase(){

    abstract val commodityDao: CommodityDao

    companion object {

        @Volatile
        private var INSTANCE: CommodityDatabase? = null

        fun getInstance(context: Context): CommodityDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CommodityDatabase::class.java,
                        "commodity_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}