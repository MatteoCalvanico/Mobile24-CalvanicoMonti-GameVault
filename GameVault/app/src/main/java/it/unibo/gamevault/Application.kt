package it.unibo.gamevault

import android.app.Application
import it.unibo.gamevault.data.local.AppDatabase
import it.unibo.gamevault.data.local.repository.AppRepository
import it.unibo.gamevault.data.local.repository.GameRepository
import it.unibo.gamevault.data.local.repository.GamesVaultRepository
import it.unibo.gamevault.data.local.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Application : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(
        context = this,
        scope = applicationScope
    ) }
    val repository by lazy { AppRepository(this, applicationScope) }

    //Redundant
    val repositoryUser by lazy { UserRepository(database.userDao()) }
    val repositoryGame by lazy { GameRepository(database.gameDao()) }
    val repositoryVault by lazy { GamesVaultRepository(database.gamesVaultDao()) }
}