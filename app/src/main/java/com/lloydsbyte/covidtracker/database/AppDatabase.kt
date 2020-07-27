package com.lloydsbyte.covidtracker.database

import androidx.room.*
import io.reactivex.Flowable


@Dao
interface  WorldDao {

    //Get List from db
    @Query("SELECT * FROM countrymodel")
    fun getWorldList(): Flowable<List<CountryModel>>

    //Add Entries
    @Transaction
    fun addWorldList(worldList: List<CountryModel>) {
        for (country in worldList) {
            addCountry(country)
        }
    }

    @Insert
    fun addCountry(vararg country: CountryModel)
}

@Database(entities = [CountryModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun WorldDao(): WorldDao
}