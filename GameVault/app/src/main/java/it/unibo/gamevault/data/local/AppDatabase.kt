package it.unibo.gamevault.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import it.unibo.gamevault.data.local.entity.UserLocalModel
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.dao.UserDao
import it.unibo.gamevault.data.local.dao.GameDao
import it.unibo.gamevault.data.local.dao.GamesVaultDao
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [UserLocalModel::class, GameLocalModel::class, GamesVaultLocalModel::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun gamesVaultDao(): GamesVaultDao

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            /* We don't need to empty the DB
            INSTANCE?.let { database ->
                scope.launch {
                    val gameDao = database.gameDao()
                    val userDao = database.userDao()
                    val gamesVaultDao = database.gamesVaultDao()

                    //Delete all content here
                    gameDao.deleteAll()
                    userDao.deleteAll()
                    gamesVaultDao.deleteAll()

                    //Add sample
                    val game = GameLocalModel("No Game", null, null, null, null, null)
                    gameDao.insertGame(game)

                    val user = UserLocalModel(0, null, null, null, null, null, null, null, null, null)

                    val games_vault = GamesVaultLocalModel("No name")
                }
            }
            */
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ):AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gameVault_database"
                ).addCallback(DatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}