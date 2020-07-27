package com.lloydsbyte.covidtracker.utilz

import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.database.StateModel
import com.lloydsbyte.covidtracker.network.UsaDataApiService
import com.lloydsbyte.covidtracker.network.WorldDataApiService

/**
 * Converts Api result models into Room compatible models
 */
class ModelConverter {
    companion object {

        fun WorldConverter(worldResultList: List<WorldDataApiService.Country>): List<CountryModel> {
            return worldResultList.map {
                CountryModel(
                    dbKey = 0,
                    countryName = it.name,
                    countryCode = it.countryCode,
                    lat = it.coordinates.latitude,
                    lng = it.coordinates.longitude,
                    population = it.population,
                    updatedAt = it.updatedAt,
                    totalDeaths = it.latestData.deaths ?: 0,
                    totalConfirmed = it.latestData.confirmed ?: 0,
                    totalRecovered = it.latestData.recovered ?: 0,
                    deathRate = it.latestData.calculated.deathRate ?: 0F,
                    recoveryRate = it.latestData.calculated.recoveryRate ?: 0F
                )
            }
        }

        fun USAConverter(countryResultList: List<UsaDataApiService.State>): List<StateModel> {
            return countryResultList.map {
                StateModel(
                    dbKey = 0,
                    stateCode = it.stateCode,
                    stateName = AppUtilz.getStateName(it.stateCode),
                    totalConfirmed = it.confirmed,
                    hospitalized = it.hospitalized,
                    totalRecovered = it.recovered,
                    updatedAt = it.lastUpdated.orEmpty(),
                    totalDeaths = it.deaths,
                    increase = it.increase
                )
            }
        }
    }
}