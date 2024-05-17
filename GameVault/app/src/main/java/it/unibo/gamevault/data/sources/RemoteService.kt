package it.unibo.gamevault.data.sources

import it.unibo.gamevault.data.sources.remotemodels.games.GamesRemoteModel
import it.unibo.gamevault.data.sources.remotemodels.games.GamesSeriesRemoteModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    //Take the single game
    @Headers(
        "X-RapidAPI-Key: 9f5975eda66a400b90008b124bc28eb9",
        "X-RapidAPI-Host: https://rawg.io/"
    )
    @GET("games")
    suspend fun getGame(
        @Query("id") id: String,
        @Query("key") apiKey: String = GamesApi.API_KEY
    ): GamesRemoteModel

    //Take all the game from the series
    @Headers(
        "X-RapidAPI-Key: 9f5975eda66a400b90008b124bc28eb9",
        "X-RapidAPI-Host: https://rawg.io/"
    )
    @GET("{game_pk}/game-series")
    suspend fun getGameSeries(
        @Path("game_pk") gamePk: String,
        @Query("key") apiKey: String = GamesApi.API_KEY
    ): GamesSeriesRemoteModel
}