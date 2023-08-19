package com.projectbyzakaria.animes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.room.Room
import com.projectbyzakaria.animes.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ModelTest {


    @Provides
    @Named("test_database")
    fun getDatabaseTest(@ApplicationContext context: Context):MovieDatabase{
        return Room.inMemoryDatabaseBuilder(context,MovieDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @Provides
    fun getBitmap(@ApplicationContext context: Context):Bitmap{
        var image = context.getDrawable(R.drawable.finish)
        return (image as BitmapDrawable).bitmap
    }




}