package com.projectbyzakaria.animes

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.projectbyzakaria.animes.data.local.MovieDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextMegration {


    @get:Rule
    var hellper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        MovieDatabase::class.java
    )



    @Test
    fun testMigration(){

        hellper.runMigrationsAndValidate("movies_local",2,true)
            .apply {
                execSQL("INSERT INTO userinfo(name,email,image,followrs)  values (null,null,null,0)")
                query("SELECT * FROM userinfo").apply{
                    assertThat(moveToFirst()).isTrue()
                    assertThat(getInt(getColumnIndex("followrs"))).isEqualTo(0)
                }
                close()
            }
    }


}