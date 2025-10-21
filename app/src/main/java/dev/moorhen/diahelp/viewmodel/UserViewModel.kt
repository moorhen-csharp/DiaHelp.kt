package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.data.model.UserModel
import dev.moorhen.diahelp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allUsers: LiveData<List<UserModel>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.getAllUsers()
    }

    fun insert(user: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUser(user)
    }

    fun update(user: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }

    fun delete(user: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUser(user)
    }

    fun getUserById(id: Int): LiveData<UserModel> {
        return repository.getUserById(id)
    }

    fun login(username: String, password: String, onResult: (Boolean, UserModel?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.loginUser(username, password)
            if (user != null) {
                onResult(true, user)
            } else {
                onResult(false, null)
            }
        }
    }
}
