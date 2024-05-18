package it.unibo.gamevault.data.sources

import it.unibo.gamevault.data.sources.remotemodels.games.GamesRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    //Take the single game
    @GET("games/{id}")
    suspend fun getGame(
        @Path("id") id: String,
        @Query("key") apiKey: String = GamesApi.API_KEY
    ): GamesRemoteModel

    //Take all the game from the series
    @GET("games/{game_pk}/game-series")
    suspend fun getGameSeries(
        @Path("game_pk") gamePk: String,
        @Query("key") apiKey: String = GamesApi.API_KEY
    ): List<GamesRemoteModel>
}