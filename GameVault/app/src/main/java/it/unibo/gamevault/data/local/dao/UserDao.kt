package it.unibo.gamevault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import it.unibo.gamevault.data.local.entity.UserLocalModel
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserLocalModel>>//uso flow perchè mi serve in UserRepository
//ho tolto 'suspend' perchè in 'UserRepository' questa funzione deve essere richiamata direttamente fuori da una coroutine o un'altra funzione di sospensione
    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int): UserLocalModel?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserLocalModel)

    @Update
    suspend fun updateUser(user: UserLocalModel)

    @Delete
    suspend fun deleteUser(user: UserLocalModel)

    @Query("DELETE FROM user")
    suspend fun deleteAll()


    @Query("SELECT API_key FROM user WHERE id = 0")
    fun getApi(): String?
}