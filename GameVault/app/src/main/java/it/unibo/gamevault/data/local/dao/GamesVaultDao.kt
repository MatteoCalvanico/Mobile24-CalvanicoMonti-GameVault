package it.unibo.gamevault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesVaultDao {
    @Query("SELECT * FROM games_vault")
    fun getAllGamesVault(): Flow<List<GamesVaultLocalModel>>//uso flow perchè mi serve in UserRepository
//ho tolto 'suspend' perchè in 'UserRepository' questa funzione deve essere richiamata direttamente fuori da una coroutine o un'altra funzione di sospensione

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGamesVault(gameVault: GamesVaultLocalModel)

    @Delete
    suspend fun deleteGamesVault(gameVault: GamesVaultLocalModel)

    @Query("DELETE FROM games_vault")
    suspend fun deleteAll()
}