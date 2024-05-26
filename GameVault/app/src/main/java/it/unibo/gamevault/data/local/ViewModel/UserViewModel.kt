package it.unibo.gamevault.data.local.ViewModel

import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import it.unibo.gamevault.data.local.Repository.UserRepository
import it.unibo.gamevault.data.local.entity.UserLocalModel
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val allUsers: LiveData<List<UserLocalModel>> = repository.allUsers.asLiveData()

    fun insert(user: UserLocalModel) = viewModelScope.launch {
        repository.insert(user)
    }

    fun update(user: UserLocalModel) = viewModelScope.launch {
        repository.update(user)
    }

    fun delete(user: UserLocalModel) = viewModelScope.launch {
        repository.delete(user)
    }

    fun getUserById(id: Int): LiveData<UserLocalModel?> {
        val user = MutableLiveData<UserLocalModel?>()
        viewModelScope.launch {
            user.value = repository.getUserById(id)
        }
        return user
    }
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}