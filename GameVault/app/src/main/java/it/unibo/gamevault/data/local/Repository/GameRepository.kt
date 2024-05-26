package it.unibo.gamevault.data.local.Repository

import androidx.annotation.WorkerThread
import it.unibo.gamevault.data.local.dao.GameDao
import it.unibo.gamevault.data.local.entity.GameLocalModel
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    // Expose the list of games as a Flow
    val allGames: Flow<List<GameLocalModel>> = gameDao.getAllGames()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(game: GameLocalModel) {
        gameDao.insertGame(game)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(game: GameLocalModel) {
        gameDao.updateGame(game)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(game: GameLocalModel) {
        gameDao.deleteGame(game)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getGameBySlug(slug: String): GameLocalModel? {
        return gameDao.getGameBySlug(slug)
    }
}