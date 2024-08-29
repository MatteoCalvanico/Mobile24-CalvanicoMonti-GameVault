package it.unibo.gamevault.data.sources

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GamesApi {

    private const val BASE_URL = "https://api.rawg.io/api/" //Base API url
    lateinit var API_KEY: String
    private const val API_HOST = "https://rawg.io/"

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
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    /**
     * rootService: lazy var for service interface
     */
    val rootService: RemoteService by lazy { retrofit.create(RemoteService::class.java) }
}