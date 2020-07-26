package com.lloydsbyte.covidtracker.network

import android.content.Context
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

class WorldDataApiService {

    data class WorldApiResult(
        @SerializedName("data")
        val data: List<Country>
    )

    data class Country(
        @SerializedName("coordinates")
        val coordinates: Coordinates
    )

    data class Coordinates(
        @SerializedName("latitude")
        val latitude: Float
    )

    interface ApiService {

        @Headers("Content-Type: application/json")
        @GET("countries")
        fun getWorldData(
        ): Observable<WorldApiResult>

        companion object {
            fun create(): ApiService {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NetworkConstants.BASE_URL)
                    .build()
                return retrofit.create(ApiService::class.java)
            }
        }
    }
}