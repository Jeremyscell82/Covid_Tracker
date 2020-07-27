package com.lloydsbyte.covidtracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryModel(
    @PrimaryKey(autoGenerate = true)
    var dbKey: Int,
    @ColumnInfo(name = "country_name")
    var countryName: String,
    @ColumnInfo(name = "country_code")
    var countryCode: String,
    @ColumnInfo(name = "lat")
    var lat: Float,
    @ColumnInfo(name = "lng")
    var lng: Float,
    @ColumnInfo(name = "population")
    var population: Int,
    @ColumnInfo(name = "updated_at")
    var updatedAt: String,
    @ColumnInfo(name = "totalDeaths")
    var totalDeaths: Int,
    @ColumnInfo(name = "totalConfirmed")
    var totalConfirmed: Int,
    @ColumnInfo(name = "totalRecovered")
    var totalRecovered: Int,
    @ColumnInfo(name ="deathRate")
    var deathRate: Float,
    @ColumnInfo(name = "recoveryRate")
    var recoveryRate: Float
)
