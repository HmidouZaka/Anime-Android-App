package com.projectbyzakaria.animes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.projectbyzakaria.animes.data.local.MovieDatabase
import com.projectbyzakaria.animes.data.local.dao.MovieDao
import com.projectbyzakaria.animes.model.MovieLocal
import com.projectbyzakaria.animes.utilt.TypeMovies
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
class TestDatabase {

    @get:Rule
    var instrumentationRunListener = InstantTaskExecutorRule()

    @get:Rule
    var testHilt = HiltAndroidRule(this)

    @Inject
    @Named("test_database")
    lateinit var database:MovieDatabase

    @Inject
    lateinit var imageBitmap :Bitmap

    lateinit var dao:MovieDao

    @Before
    fun setUp(){
        testHilt.inject()
        dao = database.getMoviesDao()
    }


    @Test
    fun testInsert() = runBlocking{
        val movie = MovieLocal(1,0,"", imageBitmap,"",TypeMovies.Anime)
        dao.insertMovie(movie)
        val value = dao.read().getOrAwaitValue()
        assertThat(value[0].id == 1).isTrue()
    }


    @Test
    fun delete() = runBlocking{
        val movie = MovieLocal(1,0,"", imageBitmap,"",TypeMovies.Anime)
        dao.insertMovie(movie)
        dao.delete(movie)
        val value = dao.read().getOrAwaitValue()
        assertThat(value.isEmpty()).isTrue()
    }


    @Test
    fun read() = runBlocking{
        val movie = MovieLocal(1,0,"", imageBitmap,"",TypeMovies.Anime)
        for (i in 1..10){
            dao.insertMovie(movie.copy(id = i))
        }
        val value = dao.read().getOrAwaitValue()
        assertThat(value.size == 10).isTrue()
    }
}