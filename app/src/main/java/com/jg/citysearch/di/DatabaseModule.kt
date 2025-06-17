package com.jg.citysearch.di

import android.content.Context
import androidx.room.Room
import com.jg.citysearch.data.local.AppDatabase
import com.jg.citysearch.data.local.dao.CityDao
import com.jg.citysearch.data.local.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "city_search_db").build()

    @Provides
    fun provideCityDao(db: AppDatabase): CityDao = db.cityDao()


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

}
