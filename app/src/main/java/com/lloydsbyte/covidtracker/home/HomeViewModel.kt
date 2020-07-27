package com.lloydsbyte.covidtracker.home

import androidx.lifecycle.ViewModel
import com.lloydsbyte.covidtracker.database.AppDatabase
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.database.StateModel
import io.reactivex.Flowable

class HomeViewModel(): ViewModel() {

    fun getWorldList(appDatabase: AppDatabase): Flowable<List<CountryModel>> {
        return appDatabase.WorldDao().getWorldList()
    }

    fun getUsaList(appDatabase: AppDatabase): Flowable<List<StateModel>> {
        return appDatabase.UsaDao().getStatesList()
    }
}