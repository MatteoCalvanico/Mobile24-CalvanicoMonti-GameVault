package it.unibo.gamevault.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import it.unibo.gamevault.data.local.entity.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<Game>

    @Query("SELECT * FROM game WHERE slug = :slug")
    suspend fun getGameBySlug(slug: String): Game?

    @Insert
    suspend fun insertGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)
}