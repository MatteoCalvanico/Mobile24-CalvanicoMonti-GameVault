package it.unibo.gamevault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.unibo.gamevault.data.local.entity.GamesVault

@Dao
interface GamesVaultDao {
    @Query("SELECT * FROM games_vault")
    suspend fun getAllGamesVault(): List<GamesVault>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameVault(gameVault: GamesVault)

    @Delete
    suspend fun deleteGameVault(gameVault: GamesVault)
}