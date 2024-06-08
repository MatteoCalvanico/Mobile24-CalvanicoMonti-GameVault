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
    fun getAllGames(): Flow<List<GameLocalModel>>

    @Query("SELECT * FROM game WHERE slug = :slug")
    suspend fun getGameBySlug(slug: String): GameLocalModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameLocalModel)

    @Update
    suspend fun updateGame(game: GameLocalModel)

    @Delete
    suspend fun deleteGame(game: GameLocalModel)

    @Query("DELETE FROM game WHERE game_name = :gameName") //Alternative deletion where you need only the slug
    suspend fun deleteGameAlt(gameName: String)

    @Query("DELETE FROM game")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM game WHERE end_date IS NOT NULL") //Return the number of game ended
    fun getCompletedGamesCount(): Flow<Int>

    @Query("SELECT AVG(your_rating) FROM game WHERE your_rating IS NOT NULL") //Return the average rating
    fun getAverageRating(): Flow<Double>
}