package it.unibo.gamevault.ui.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import it.unibo.gamevault.data.local.Repository.AppRepository
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import kotlinx.coroutines.launch

class VaultViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepository = AppRepository.getInstance(application, viewModelScope)
    val combinedGamesVault: LiveData<List<GameLocalModel>> = repository.getCombinedGamesVault().asLiveData()

    fun insert(gamesVault: GamesVaultLocalModel) = viewModelScope.launch {
        repository.insertGamesVault(gamesVault)
    }

    fun delete(gamesVault: GamesVaultLocalModel) = viewModelScope.launch {
        repository.deleteGamesVault(gamesVault)
    }
}
