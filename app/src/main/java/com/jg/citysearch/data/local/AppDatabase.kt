package com.jg.citysearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jg.citysearch.data.local.dao.CityDao
import com.jg.citysearch.data.local.entity.CityEntity

@Database(
    entities = [CityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}