package it.unibo.gamevault.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import it.unibo.gamevault.data.local.Repository.AppRepository
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.entity.UserLocalModel
import it.unibo.gamevault.data.sources.GamesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageViewModel(private val repository: AppRepository) : ViewModel() {
    // LiveData to hold user data
    private val _userData = MutableLiveData<UserLocalModel?>()
    val userData: LiveData<UserLocalModel?> = _userData

    // LiveData to hold favorite games
    private val _favoriteGames = MutableLiveData<List<GameLocalModel>>()
    val favoriteGames: MutableLiveData<List<GameLocalModel>> = _favoriteGames

    //Three LiveData to hold user stats
    val meanRating: LiveData<Double> = repository.getAverageRating().asLiveData()
    val totalGameCount: LiveData<Int> = repository.getTotalGameCount().asLiveData()
    val gameEnded: LiveData<Int> = repository.getCompletedGamesCount().asLiveData()



    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val user = repository.getUserById(0)
            _userData.postValue(user) //Update LiveData on the main thread

            //Load favorite games
            val games = mutableListOf<GameLocalModel?>()
            user?.favouriteOne?.let { games.add(repository.getGameBySlug(it)) }
            user?.favouriteTwo?.let { games.add(repository.getGameBySlug(it)) }
            user?.favouriteThree?.let { games.add(repository.getGameBySlug(it)) }
            user?.favouriteFour?.let { games.add(repository.getGameBySlug(it)) }

            //Need this to remove the null and use the LiveData without problem
            val nonNullGames = games.filterNotNull()
            _favoriteGames.postValue(nonNullGames)

            //Update the API saved in GamesApi.kt
            withContext(Dispatchers.IO){
                repository.updateGamesApi()
            }
        }
    }
}
