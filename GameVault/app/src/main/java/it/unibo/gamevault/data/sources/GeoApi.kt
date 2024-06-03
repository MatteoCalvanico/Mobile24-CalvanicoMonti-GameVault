package it.unibo.gamevault.data.sources

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GeoApi {

    private const val BASE_URL = "https://places.googleapis.com/v1/" //Base API url
    const val API_KEY = "AIzaSyAj5skOkzzUek76Ziqf_90QEdt6VY6WuAY" //key for api

    /**
     * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
     */
    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * The Retrofit object with the Moshi converter.
     */
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    /**
     * lazy var for service interface
     */
    val geolocationApi: GeoRemoteService = retrofit.create(GeoRemoteService::class.java)
}