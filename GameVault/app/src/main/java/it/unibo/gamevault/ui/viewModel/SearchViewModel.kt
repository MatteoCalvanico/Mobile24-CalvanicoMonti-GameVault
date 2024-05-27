package it.unibo.gamevault.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unibo.gamevault.R
import it.unibo.gamevault.data.sources.repositories.GameRepository
import it.unibo.gamevault.ui.model.Game
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel(private val gameRepository: GameRepository) : ViewModel() {

    //Search result with LiveData
    private val _searchResults = MutableLiveData<List<Game>>()
    val searchResults: LiveData<List<Game>> = _searchResults

    //Error type with LiveData
    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    // ProgressBar visibility LiveData
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchGames(query: String, queryType: Int) {
        _isLoading.postValue(true) //Start ProgressBar
        _searchResults.postValue(emptyList()) //Clear the old result to empty the recycler
        viewModelScope.launch {
            try {
                val result = when (queryType) {
                    R.id.optionSeries -> gameRepository.getGameSeries(query)
                    R.id.optionGame -> listOf(gameRepository.getGame(query))
                    else -> emptyList()
                }
                _searchResults.postValue(result)
            }  catch (e: HttpException) {
                if (e.code() == 404 or 502) {
                    _error.postValue(1) //If the API return 404 or 502
                } else {
                    _error.postValue(0) //Other error with the API
                }
            } catch (e: Exception) {
                _error.postValue(-1) //Generic error
            } finally {
                _isLoading.postValue(false) //Stop ProgressBar
            }
        }
    }
}