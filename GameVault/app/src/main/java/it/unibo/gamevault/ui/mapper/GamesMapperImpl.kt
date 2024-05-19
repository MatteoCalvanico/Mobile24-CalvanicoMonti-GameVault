package it.unibo.gamevault.ui.mapper

import android.util.Log
import it.unibo.gamevault.data.sources.remotemodels.games.GameSeriesRemoteModel
import it.unibo.gamevault.data.sources.remotemodels.games.GamesRemoteModel
import it.unibo.gamevault.ui.model.Game
import it.unibo.gamevault.ui.model.Platform

class GameMapperImpl : GameMapper {
    override fun toGame(gamesRemoteModel: GamesRemoteModel): Game {
        val platforms = gamesRemoteModel.platforms?.map { Platform(it.platform.name) }
        return Game(
            gamesRemoteModel.id,
            gamesRemoteModel.slug,
            gamesRemoteModel.name,
            gamesRemoteModel.description,
            gamesRemoteModel.metacritic,
            gamesRemoteModel.released,
            gamesRemoteModel.tba,
            gamesRemoteModel.backgroundImage,
            platforms
        )
    }

    override fun toGameList(gamesSeriesRemoteModel: GameSeriesRemoteModel): List<Game> {
        val series: List<Game> = emptyList()

        for (game in gamesSeriesRemoteModel.result!!){
            series.map {
                Game(
                    game.id.toString(),
                    game.slug,
                    game.name,
                    "",
                    game.metacritic,
                    game.released,
                    game.tba,
                    game.backgroundImage,
                    game.platforms?.map { Platform(it.platform.name)}
                )
            }
        }
        Log.d("GameMapperLog", series.toString())
        return series
    }
}
