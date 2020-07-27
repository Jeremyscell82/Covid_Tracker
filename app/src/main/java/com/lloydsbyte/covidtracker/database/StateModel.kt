package com.lloydsbyte.covidtracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StateModel(
    @PrimaryKey(autoGenerate = true)
    var dbKey: Int,
    @ColumnInfo(name = "state_code")
    var stateCode: String,
    @ColumnInfo(name = "state_name")
    var stateName: String,
    @ColumnInfo(name = "totalConfirmed")
    var totalConfirmed: Int,
    @ColumnInfo(name = "hospitalized")
    var hospitalized: Int,
    @ColumnInfo(name = "totalRecovered")
    var totalRecovered: Int,
    @ColumnInfo(name = "updatedAt")
    var updatedAt: String,
    @ColumnInfo(name = "totalDeaths")
    var totalDeaths: Int,
    @ColumnInfo(name = "increase")
    var increase: Int
)