package com.projectbyzakaria.animes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.projectbyzakaria.animes.model.MovieLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieLocal: MovieLocal)

    @Delete
    suspend fun delete(movieLocal: MovieLocal)

    @Query("SELECT * FROM movies")
    fun read():LiveData<List<MovieLocal>>


    @Query("SELECT count(*) FROM movies where mel_id = :id")
    suspend fun isInDatabase(id:Int):Int

    @Query("SELECT count(*) FROM movies")
    fun getCountFavorite():Flow<Int>
}