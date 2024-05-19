package it.unibo.gamevault.ui.mapper

import it.unibo.gamevault.data.sources.remotemodels.games.GameSeriesRemoteModel
import it.unibo.gamevault.data.sources.remotemodels.games.GamesRemoteModel
import it.unibo.gamevault.ui.model.Game

interface GameMapper {
    fun toGame(gamesRemoteModel: GamesRemoteModel): Game
    fun toGameList(gamesSeriesRemoteModel: GameSeriesRemoteModel): List<Game>
}