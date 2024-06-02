package it.unibo.gamevault.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.Repository.AppRepository
import kotlinx.coroutines.launch

/**
 * ViewModel used between AddFavoriteDialog and SettingsActivity to obtain the favorite game chosen by the user
 */
class SharedViewModel(private val repository: AppRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<GameLocalModel?>()
    val gameResult: LiveData<GameLocalModel?> = _searchResults

    //Error type with LiveData
    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    //ProgressBar visibility LiveData
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchGame(query: String) {
        _isLoading.postValue(true) //Start ProgressBar

        viewModelScope.launch {
            try {
                val formattedQuery = query.trim().lowercase().replace(" ", "-")
                val game = repository.getGameBySlug(formattedQuery)
                _searchResults.value = game
            } catch (e: Exception) {
                _error.postValue(e) //Post error
            } finally {
                _isLoading.postValue(false) //Stop ProgressBar
            }
        }
    }

    fun clearGameResult() {
        _searchResults.value = null
    }
}
