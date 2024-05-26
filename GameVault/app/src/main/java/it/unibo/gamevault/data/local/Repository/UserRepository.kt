package it.unibo.gamevault.data.local.Repository

import androidx.annotation.WorkerThread
import it.unibo.gamevault.data.local.dao.UserDao
import it.unibo.gamevault.data.local.entity.UserLocalModel
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    // Expose the list of users as a Flow
    val allUsers: Flow<List<UserLocalModel>> = userDao.getAllUsers()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: UserLocalModel) {
        userDao.insertUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(user: UserLocalModel) {
        userDao.updateUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(user: UserLocalModel) {
        userDao.deleteUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUserById(id: Int): UserLocalModel? {
        return userDao.getUserById(id)
    }
}