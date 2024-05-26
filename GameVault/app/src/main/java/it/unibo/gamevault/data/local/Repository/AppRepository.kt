package it.unibo.gamevault.data.local.Repository

import android.content.Context
import androidx.annotation.WorkerThread
import it.unibo.gamevault.data.local.AppDatabase
import it.unibo.gamevault.data.local.entity.UserLocalModel
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import kotlinx.coroutines.flow.Flow

class AppRepository private constructor(context: Context) {

    private val userDao = AppDatabase.getDatabase(context).userDao()
    private val gameDao = AppDatabase.getDatabase(context).gameDao()
    private val gamesVaultDao = AppDatabase.getDatabase(context).gamesVaultDao()

    // User-related methods
    val allUsers: Flow<List<UserLocalModel>> = userDao.getAllUsers()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user: UserLocalModel) {
        userDao.insertUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user: UserLocalModel) {
        userDao.updateUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteUser(user: UserLocalModel) {
        userDao.deleteUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUserById(id: Int): UserLocalModel? {
        return userDao.getUserById(id)
    }

    // Game-related methods
    val allGames: Flow<List<GameLocalModel>> = gameDao.getAllGames()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertGame(game: GameLocalModel) {
        gameDao.insertGame(game)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateGame(game: GameLocalModel) {
        gameDao.updateGame(game)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteGame(game: GameLocalModel) {
        gameDao.deleteGame(game)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getGameBySlug(slug: String): GameLocalModel? {
        return gameDao.getGameBySlug(slug)
    }

    // GamesVault-related methods
    val allGamesVault: Flow<List<GamesVaultLocalModel>> = gamesVaultDao.getAllGamesVault()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertGamesVault(gamesVault: GamesVaultLocalModel) {
        gamesVaultDao.insertGamesVault(gamesVault)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteGamesVault(gamesVault: GamesVaultLocalModel) {
        gamesVaultDao.deleteGamesVault(gamesVault)
    }

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context): AppRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = AppRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}