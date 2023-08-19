package com.projectbyzakaria.animes

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.projectbyzakaria.animes.data.local.MovieDatabase
import com.projectbyzakaria.animes.data.local.dao.UserDao
import com.projectbyzakaria.animes.model.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class TestUserTable {

    @get:Rule
    var intremintal = InstantTaskExecutorRule()


    @get:Rule
    var hilt = HiltAndroidRule(this)

    @Inject
    lateinit var database:MovieDatabase

    lateinit var userDao :UserDao

    @Before
    fun setUp(){
        hilt.inject()
        userDao = database.getUserDao()
    }


    @Test
    fun testInsertUserEmpty() = runBlocking{
        var user = User()
        userDao.insertUser(user)
        var response = userDao.getUser()
        assertThat(response).isEqualTo(user)
    }

    @Test
    fun testInsertUserWhitData() = runBlocking{
        var context = ApplicationProvider.getApplicationContext<Context>()
        var drawable  = context.getDrawable(R.drawable.error)
        var bitmap = (drawable as BitmapDrawable).bitmap
        var user = User("zakaria","email@test.com",bitmap,20)
        userDao.insertUser(user)
        var response = userDao.getUser()
        assertThat(response.name).isEqualTo(user.name)
        assertThat(response.email).isEqualTo(user.email)
        assertThat(response.image?.byteCount).isEqualTo(user.image?.byteCount)
        assertThat(response.followrs).isEqualTo(user.followrs)
    }


    @Test
    fun testUpdateUser()= runBlocking{
        var context = ApplicationProvider.getApplicationContext<Context>()
        var drawable  = context.getDrawable(R.drawable.error)
        var bitmap = (drawable as BitmapDrawable).bitmap
        var user = User("zakaria","email@test.com",bitmap,20)
        userDao.insertUser(user)
        var newUser = User("hmidou","email@test.com",bitmap,20)
        userDao.updateUser(newUser)
        var response = userDao.getUser()
        assertThat(response.name).isEqualTo(newUser.name)
        assertThat(response.email).isEqualTo(newUser.email)
        assertThat(response.image?.byteCount).isEqualTo(newUser.image?.byteCount)
        assertThat(response.followrs).isEqualTo(newUser.followrs)
    }


    @Test
    fun testIsHaveUserInDataBase() = runBlocking{
        val user = User("zakaria","email@test.com",null,20)
        userDao.insertUser(user)
        assertThat(userDao.isThereUser()).isEqualTo(1)
    }

    @Test
    fun testaddUserIfHaseOne() = runBlocking{
        val user = User("zakaria","email@test.com",null,20)
        for (i in 0..10){
            if (userDao.isThereUser() == 0){
                userDao.insertUser(user.copy(followrs = i))
            }
        }
        assertThat(userDao.isThereUser()).isEqualTo(1)
    }








}