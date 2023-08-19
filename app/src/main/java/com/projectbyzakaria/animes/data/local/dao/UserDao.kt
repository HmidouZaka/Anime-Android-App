package com.projectbyzakaria.animes.data.local.dao

import androidx.room.*
import com.projectbyzakaria.animes.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:User)

    @Query("SELECT * FROM userinfo LIMIT 1")
    suspend fun getUser():User


    @Query("SELECT count(*) from userinfo")
    suspend fun isThereUser():Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

}