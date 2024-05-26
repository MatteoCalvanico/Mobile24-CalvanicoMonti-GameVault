package it.unibo.gamevault.data.local.ViewModel

import androidx.lifecycle.*
import it.unibo.gamevault.data.local.Repository.GameRepository
import it.unibo.gamevault.data.local.entity.GameLocalModel
import kotlinx.coroutines.launch

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    val allGames: LiveData<List<GameLocalModel>> = repository.allGames.asLiveData()

    fun insert(game: GameLocalModel) = viewModelScope.launch {
        repository.insert(game)
    }

    fun update(game: GameLocalModel) = viewModelScope.launch {
        repository.update(game)
    }

    fun delete(game: GameLocalModel) = viewModelScope.launch {
        repository.delete(game)
    }

    fun getGameBySlug(slug: String): LiveData<GameLocalModel?> {
        val game = MutableLiveData<GameLocalModel?>()
        viewModelScope.launch {
            game.value = repository.getGameBySlug(slug)
        }
        return game
    }
}

class GameViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}