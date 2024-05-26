package it.unibo.gamevault.data.local.Repository

import androidx.annotation.WorkerThread
import it.unibo.gamevault.data.local.dao.GamesVaultDao
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import kotlinx.coroutines.flow.Flow

class GamesVaultRepository(private val gamesVaultDao: GamesVaultDao) {

    // Expose the list of games vault as a Flow
    val allGamesVault: Flow<List<GamesVaultLocalModel>> = gamesVaultDao.getAllGamesVault()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(gamesVault: GamesVaultLocalModel) {
        gamesVaultDao.insertGamesVault(gamesVault)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(gamesVault: GamesVaultLocalModel) {
        gamesVaultDao.deleteGamesVault(gamesVault)
    }
}