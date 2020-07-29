package com.lloydsbyte.covidtracker.network

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

class UsaDataApiService {

    data class State(
        @SerializedName("state")
        val stateCode: String,
        @SerializedName("positive")
        val confirmed: Int,
        @SerializedName("hospitalizedCurrently")
        val hospitalized: Int,
        @SerializedName("recovered")
        val recovered: Int,
        @SerializedName("lastUpdatedEt")
        val lastUpdated: String?,
        @SerializedName("death")
        val deaths: Int,
        @SerializedName("positiveIncrease")
        val increase: Int
    )


    /** ======================= Individual Api Result =================== **/
    data class StateHistory(
        @SerializedName("lastUpdatedEt")
        val updatedAt: String,
        @SerializedName("deaths")
        val deaths: Int,
        @SerializedName("positive")
        val confirmed: Int,
        @SerializedName("positiveIncrease")
        val newConfirmed: Int,
        @SerializedName("deathIncrease")
        val newDeaths: Int
    )


    interface ApiService {
        @Headers("Content-Type: application/json")
        @GET("states/current.json")
        fun pullUsaData(): Observable<List<State>>

        @Headers("Content-Type: application/json")
        @GET("states/{code}/daily.json")
        fun pullStateData(@Path("code") code: String): Observable<List<StateHistory>>

        companion object {
            fun create(): ApiService {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NetworkConstants.USA_BASE_URL)
                    .build()
                return retrofit.create(UsaDataApiService.ApiService::class.java)
            }
        }
    }
}