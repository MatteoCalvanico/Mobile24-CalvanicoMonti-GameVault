package it.unibo.gamevault.data.local.Repository

import android.content.Context
import androidx.annotation.WorkerThread
import it.unibo.gamevault.data.local.AppDatabase
import it.unibo.gamevault.data.local.entity.UserLocalModel
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * We need this for use all the DAO
 */
class AppRepository(context: Context, scope: CoroutineScope) {

    private val userDao = AppDatabase.getDatabase(context, scope).userDao()
    private val gameDao = AppDatabase.getDatabase(context, scope).gameDao()
    private val gamesVaultDao = AppDatabase.getDatabase(context, scope).gamesVaultDao()

    // User-related methods
    val allUsers: Flow<List<UserLocalModel>> = userDao.getAllUsers()

    @WorkerThread
    suspend fun insertUser(user: UserLocalModel) {
        userDao.insertUser(user)
    }

    @WorkerThread
    suspend fun updateUser(user: UserLocalModel) {
        userDao.updateUser(user)
    }

    @WorkerThread
    suspend fun deleteUser(user: UserLocalModel) {
        userDao.deleteUser(user)
    }

    @WorkerThread
    suspend fun getUserById(id: Int): UserLocalModel? {
        return userDao.getUserById(id)
    }

    // Game-related methods
    val allGames: Flow<List<GameLocalModel>> = gameDao.getAllGames()

    @WorkerThread
    suspend fun insertGame(game: GameLocalModel) {
        gameDao.insertGame(game)
    }

    @WorkerThread
    suspend fun updateGame(game: GameLocalModel) {
        gameDao.updateGame(game)
    }

    @WorkerThread
    suspend fun deleteGame(game: GameLocalModel) {
        gameDao.deleteGame(game)
    }

    @WorkerThread
    suspend fun getGameBySlug(slug: String): GameLocalModel? {
        return gameDao.getGameBySlug(slug)
    }

    // GamesVault-related methods
    val allGamesVault: Flow<List<GamesVaultLocalModel>> = gamesVaultDao.getAllGamesVault()

    @WorkerThread
    suspend fun insertGamesVault(gamesVault: GamesVaultLocalModel) {
        gamesVaultDao.insertGamesVault(gamesVault)
    }

    @WorkerThread
    suspend fun deleteGamesVault(gamesVault: GamesVaultLocalModel) {
        gamesVaultDao.deleteGamesVault(gamesVault)
    }

    // Need this for have all the vault games with details
    fun getCombinedGamesVault(): Flow<List<GameLocalModel>> {
        return allGamesVault.map { gamesVaultList ->
            gamesVaultList.mapNotNull { vaultGame ->
                getGameBySlug(vaultGame.gameName)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = AppRepository(context, scope)
                INSTANCE = instance
                instance
            }
        }
    }
}