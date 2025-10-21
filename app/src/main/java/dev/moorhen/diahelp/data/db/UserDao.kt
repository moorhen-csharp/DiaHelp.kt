package dev.moorhen.diahelp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.moorhen.diahelp.data.model.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): UserModel?

    @Query("SELECT * FROM users WHERE username = :username OR email = :email")
    suspend fun getUserByUsernameOrEmail(username: String, email: String): UserModel?
    fun getAllUsers(): LiveData<List<UserModel>>
}
