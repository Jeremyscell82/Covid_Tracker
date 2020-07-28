package com.lloydsbyte.covidtracker.network

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

class WorldDataApiService {

    data class WorldApiResult(
        @SerializedName("data")
        val data: List<Country>
    )

    data class Country(
        @SerializedName("coordinates")
        val coordinates: Coordinates,
        @SerializedName("name")
        val name: String,
        @SerializedName("code")
        val countryCode: String,
        @SerializedName("population")
        val population: Int,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("latest_data")
        val latestData: LatestData
    )

    data class Coordinates(
        @SerializedName("latitude")
        val latitude: Float,
        @SerializedName("longitude")
        val longitude: Float
    )

    data class LatestData(
        @SerializedName("deaths")
        val deaths: Int?,
        @SerializedName("confirmed")
        val confirmed: Int?,
        @SerializedName("recovered")
        val recovered: Int?,
        @SerializedName("calculated")
        val calculated: Calculated
    )

    data class Calculated(
        @SerializedName("death_rate")
        val deathRate: Float?,
        @SerializedName("recovery_rate")
        val recoveryRate: Float?
    )

    /** ======================= Individual Api Result =================== **/
    data class CountryResult(
        @SerializedName("data")
        val data: CountryData
    )

    data class CountryData(
        @SerializedName("timeline")
        val timeline: List<Timeline>
    )

    data class Timeline(
        @SerializedName("update_at")
        val updateAt: String,
        @SerializedName("deaths")
        val deaths: Int,
        @SerializedName("confirmed")
        val confirmed: Int,
        @SerializedName("new_confirmed")
        val newConfirmed: Int,
        @SerializedName("new_recovered")
        val newRecovered: Int,
        @SerializedName("new_deaths")
        val newDeaths: Int,
        @SerializedName("is_in_progress")
        val isInProgress: Boolean,
        @SerializedName("active")
        val active: Int
        )

    interface ApiService {

        @Headers("Content-Type: application/json")
        @GET("countries")
        fun pullWorldData(): Observable<WorldApiResult>

        @Headers("Content-Type: application/json")
        @GET("countries/{code}")
        fun pullCountryData(@Path("code") code: String): Observable<CountryResult>

        companion object {
            fun create(): ApiService {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NetworkConstants.WORLD_BASE_URL)
                    .build()
                return retrofit.create(ApiService::class.java)
            }
        }
    }
}