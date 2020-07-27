package com.lloydsbyte.covidtracker.database

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface UsaDao {

    //Get List from db
    @Query("SELECT * FROM statemodel")
    fun getStatesList(): Flowable<List<StateModel>>

    @Transaction
    fun addStatesList(statesList: List<StateModel>) {
        for (state in statesList){
            addState(state)
        }
    }

    @Insert
    fun addState(vararg state: StateModel)
}

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

@Database(entities = [CountryModel::class, StateModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun WorldDao(): WorldDao
    abstract fun UsaDao(): UsaDao
}