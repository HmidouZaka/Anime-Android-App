package com.projectbyzakaria.animes

import com.projectbyzakaria.animes.utilt.runIfQueryNotEmpty
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `test fun is work fine`(){
        var fin = ""
        val testStr = "".runIfQueryNotEmpty { fin = it }
        assertEquals(fin,"")
    }
}