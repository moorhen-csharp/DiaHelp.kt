package dev.moorhen.diahelp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.moorhen.diahelp.data.model.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel)

    @Update
    suspend fun updateUser(user: UserModel)

    @Delete
    suspend fun deleteUser(user: UserModel)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun loginUser(username: String, password: String): UserModel?

    @Query("SELECT * FROM users WHERE username = :username OR email = :email LIMIT 1")
    suspend fun getUserByUsernameOrEmail(username: String, email: String): UserModel?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: Int): LiveData<UserModel>

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<UserModel>>
}
