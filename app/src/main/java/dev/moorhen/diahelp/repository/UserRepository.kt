package dev.moorhen.diahelp.repository

import androidx.lifecycle.LiveData
import dev.moorhen.diahelp.data.db.UserDao
import dev.moorhen.diahelp.data.model.UserModel

class UserRepository(private val userDao: UserDao) {

    fun getAllUsers(): LiveData<List<UserModel>> = userDao.getAllUsers()

    suspend fun insertUser(user: UserModel) = userDao.insertUser(user)

    suspend fun updateUser(user: UserModel) = userDao.updateUser(user)

    suspend fun deleteUser(user: UserModel) = userDao.deleteUser(user)

    fun getUserById(id: Int): LiveData<UserModel> = userDao.getUserById(id)

    suspend fun loginUser(username: String, password: String): UserModel? =
        userDao.loginUser(username, password)

    suspend fun getUserByUsernameOrEmail(username: String, email: String): UserModel? =
        userDao.getUserByUsernameOrEmail(username, email)
}
