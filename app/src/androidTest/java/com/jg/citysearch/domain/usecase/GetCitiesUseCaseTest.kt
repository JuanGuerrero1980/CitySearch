package com.jg.citysearch.domain.usecase


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jg.citysearch.data.local.AppDatabase
import com.jg.citysearch.data.local.dao.CityDao
import com.jg.citysearch.data.local.entity.CityEntity
import com.jg.citysearch.data.repository.toDomainModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class GetCitiesUseCaseTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: CityDao

    @Before
    fun setUp() {

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.cityDao()

        runBlocking {
            dao.insertAll(
                listOf(
                    CityEntity(
                        id = 1,
                        name = "Alabama",
                        country = "US",
                        longitude = 0.0,
                        latitude = 0.0,
                        isFavorite = true
                    ),
                    CityEntity(
                        id = 2,
                        name = "Albuquerque",
                        country = "US",
                        longitude = 0.0,
                        latitude = 0.0,
                        isFavorite = false
                    ),
                    CityEntity(
                        id = 3,
                        name = "Anaheim",
                        country = "US",
                        longitude = 0.0,
                        latitude = 0.0,
                        isFavorite = true
                    ),
                    CityEntity(
                        id = 4,
                        name = "Arizona",
                        country = "US",
                        longitude = 0.0,
                        latitude = 0.0,
                        isFavorite = false
                    ),
                    CityEntity(
                        id = 5,
                        name = "Sydney",
                        country = "AU",
                        longitude = 0.0,
                        latitude = 0.0,
                        isFavorite = false
                    ),
                )
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFilteredFavorites() = runTest {
        // Insert test data is already done in setUp()
        val list = dao.getCities(query = "", onlyFavorites = true)
        val items = list.map { it.toDomainModel() }

        assertEquals(2, items.size)
        assertEquals("Alabama", items[0].name)
        assertEquals("Anaheim", items[1].name)
    }

    @Test
    fun testFilteredByPrefix() = runTest {
        // If the given prefix is "A", all cities but Sydney should appear. Contrariwise, if the given prefix is "s", the only result should be "Sydney, AU"
        // Insert test data is already done in setUp()
        var list = dao.getCities(query = "A", onlyFavorites = false)
        var items = list.map { it.toDomainModel() }

        assertEquals(4, items.size)
        assertEquals("Alabama", items[0].name)
        assertEquals("Albuquerque", items[1].name)
        assertEquals("Anaheim", items[2].name)
        assertEquals("Arizona", items[3].name)

        list = dao.getCities(query = "S", onlyFavorites = false)
        items = list.map { it.toDomainModel() }

        assertEquals(1, items.size)
        assertEquals("Sydney", items[0].name)

        // If the given prefix is "Al", "Alabama, US" and "Albuquerque, US" are the only results.
        list = dao.getCities(query = "Al", onlyFavorites = false)
        items = list.map { it.toDomainModel() }

        assertEquals(2, items.size)
        assertEquals("Alabama", items[0].name)
        assertEquals("Albuquerque", items[1].name)

        // If the prefix given is "Alb" then the only result is "Albuquerque, US"
        list = dao.getCities(query = "Alb", onlyFavorites = false)
        items = list.map { it.toDomainModel() }

        assertEquals(1, items.size)
        assertEquals("Albuquerque", items[0].name)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}
