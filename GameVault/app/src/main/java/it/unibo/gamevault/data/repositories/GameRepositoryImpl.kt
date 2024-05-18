package it.unibo.gamevault.data.repositories

import it.unibo.gamevault.data.sources.RemoteService
import it.unibo.gamevault.ui.mapper.GameMapper
import it.unibo.gamevault.ui.model.Game

class GameRepositoryImpl(
    private val remoteService: RemoteService,
    private val gameMapper: GameMapper
) : GameRepository {


    override suspend fun getGame(id: String): Game {
            val remoteGame = remoteService.getGame(id)
            return gameMapper.toGame(remoteGame)
    }

    override suspend fun getGameSeries(gamePk: String): List<Game> {
        val remoteGames = remoteService.getGameSeries(gamePk)
        return gameMapper.toGameList(remoteGames)
    }
}
