package com.projectbyzakaria.animes.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.projectbyzakaria.animes.data.local.convereters.ConvertImage
import com.projectbyzakaria.animes.data.local.convereters.ConvertImageToNull
import com.projectbyzakaria.animes.data.local.convereters.ConvertType
import com.projectbyzakaria.animes.data.local.dao.MovieDao
import com.projectbyzakaria.animes.data.local.dao.UserDao
import com.projectbyzakaria.animes.model.MovieLocal
import com.projectbyzakaria.animes.model.User

@Database([MovieLocal::class,User::class], version = 2, autoMigrations = [AutoMigration(1,2)])
@TypeConverters(value = [ConvertImageToNull::class,ConvertType::class])
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMoviesDao():MovieDao
    abstract fun getUserDao():UserDao
}