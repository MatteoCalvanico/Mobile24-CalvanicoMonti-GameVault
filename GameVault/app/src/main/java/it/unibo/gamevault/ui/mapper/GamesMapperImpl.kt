package it.unibo.gamevault.ui.mapper

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

    override fun toGameList(gamesSeriesRemoteModel: List<GamesRemoteModel>): List<Game> {
        return gamesSeriesRemoteModel.map { toGame(it) }
    }
}
