package com.lloydsbyte.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.lloydsbyte.covidtracker.database.AppDatabase
import com.lloydsbyte.covidtracker.home.HomeViewPagerFragment
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.database.StateModel
import com.lloydsbyte.covidtracker.utilz.AppUtilz
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "com.lloydsbyte.todoos.db"
        ).build()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.main_container,
                    HomeViewPagerFragment()
                )
                .commit()
        }
        version_view.text = BuildConfig.VERSION_NAME
        if (AppUtilz.checkConnection(this)){
            clearData()
        } else {
            //Todo display dialog of no connection and to load last update (save date in shared preferences)
        }
    }


    /**
     * Database controls
     */
    fun saveWorldData(countriesList: List<CountryModel>) {
        Timber.d("JL_ Saving ${countriesList.size} countries to the database")
        GlobalScope.launch {
            appDatabase.WorldDao().addWorldList(countriesList)
        }
    }

    fun saveUsaData(stateList: List<StateModel>) {
        Timber.d("JL_ Saving ${stateList.size} states to the database")
        GlobalScope.launch {
            appDatabase.UsaDao().addStatesList(stateList)
        }
    }

    private fun clearData(){
        Timber.d("JL_ clearing data")
        GlobalScope.launch {
            appDatabase.clearAllTables()
        }
    }
}