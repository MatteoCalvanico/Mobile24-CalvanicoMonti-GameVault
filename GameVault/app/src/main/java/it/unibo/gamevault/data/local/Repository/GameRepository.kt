package it.unibo.gamevault.data.local.Repository

import androidx.annotation.WorkerThread
import it.unibo.gamevault.data.local.dao.GameDao
import it.unibo.gamevault.data.local.entity.GameLocalModel
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    // Expose the list of games as a Flow
    val allGames: Flow<List<GameLocalModel>> = gameDao.getAllGames()

    @WorkerThread
    suspend fun insert(game: GameLocalModel) {
        gameDao.insertGame(game)
    }

    @WorkerThread
    suspend fun update(game: GameLocalModel) {
        gameDao.updateGame(game)
    }

    @WorkerThread
    suspend fun delete(game: GameLocalModel) {
        gameDao.deleteGame(game)
    }

    @WorkerThread
    suspend fun getGameBySlug(slug: String): GameLocalModel? {
        return gameDao.getGameBySlug(slug)
    }
}