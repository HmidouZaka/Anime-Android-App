package com.projectbyzakaria.animes.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projectbyzakaria.animes.data.local.MovieDatabase
import com.projectbyzakaria.animes.data.remote.apis.apI_anime.ApiAnimeWhetServer1
import com.projectbyzakaria.animes.data.remote.apis.api_manga.ApiMangaWhetServer1
import com.projectbyzakaria.animes.utilt.Constent
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Named("server1")
    @Provides
    fun retrofitWithServer1() = Retrofit.Builder()
        .baseUrl(Constent.BASE_URL_FOR_SERVER1)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun getApiWhitServer1ForAnime(@Named("server1") retrofit: Retrofit):ApiAnimeWhetServer1{
        val api by lazy {
            retrofit.create(ApiAnimeWhetServer1::class.java)
        }
        return api
    }


    @Singleton
    @Provides
    fun getApiWhitServer1ForManga(@Named("server1") retrofit: Retrofit):ApiMangaWhetServer1{
        val api by lazy {
            retrofit.create(ApiMangaWhetServer1::class.java)
        }
        return api
    }

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context:Context) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        "movies_local"
        ).build()




}