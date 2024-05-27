package it.unibo.gamevault.data.sources.repositories

import it.unibo.gamevault.ui.model.Game

interface GameRepository {
    suspend fun getGame(id: String): Game
    suspend fun getGameSeries(gamePk: String): List<Game>
}