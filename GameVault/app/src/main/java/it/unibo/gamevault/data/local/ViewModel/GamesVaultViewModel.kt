package it.unibo.gamevault.data.local.ViewModel

import androidx.lifecycle.*
import it.unibo.gamevault.data.local.Repository.GamesVaultRepository
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import kotlinx.coroutines.launch

class GamesVaultViewModel(private val repository: GamesVaultRepository) : ViewModel() {

    val allGamesVault: LiveData<List<GamesVaultLocalModel>> = repository.allGamesVault.asLiveData()

    fun insert(gameVault: GamesVaultLocalModel) = viewModelScope.launch {
        repository.insert(gameVault)
    }

    fun delete(gameVault: GamesVaultLocalModel) = viewModelScope.launch {
        repository.delete(gameVault)
    }
}

class GamesVaultViewModelFactory(private val repository: GamesVaultRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GamesVaultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GamesVaultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}