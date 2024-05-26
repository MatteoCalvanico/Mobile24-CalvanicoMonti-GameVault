package it.unibo.gamevault.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import it.unibo.gamevault.data.local.entity.UserLocalModel
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.dao.UserDao
import it.unibo.gamevault.data.local.dao.GameDao
import it.unibo.gamevault.data.local.dao.GamesVaultDao
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(
    entities = [UserLocalModel::class, GameLocalModel::class, GamesVaultLocalModel::class],
    version = 1,
    exportSchema = false
)

public abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gameVault_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun gamesVaultDao(): GamesVaultDao
}