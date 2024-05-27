package it.unibo.gamevault.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import it.unibo.gamevault.data.local.entity.GameLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAllGames(): Flow<List<GameLocalModel>>//uso flow perchè mi serve in UserRepository
//ho tolto 'suspend' perchè in 'UserRepository' questa funzione deve essere richiamata direttamente fuori da una coroutine o un'altra funzione di sospensione

    @Query("SELECT * FROM game WHERE slug = :slug")
    suspend fun getGameBySlug(slug: String): GameLocalModel?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGame(game: GameLocalModel)

    @Update
    suspend fun updateGame(game: GameLocalModel)

    @Delete
    suspend fun deleteGame(game: GameLocalModel)

    @Query("DELETE FROM game")
    suspend fun deleteAll()
}