package com.projectbyzakaria.animes

import com.google.common.truth.Truth.assertThat
import com.projectbyzakaria.animes.utilt.isValidEmail
import com.projectbyzakaria.animes.utilt.uniqueList
import org.junit.Rule
import org.junit.Test

class TextHelper {



    data class Persson(val id:Int,val name:String,val age:Int){
        override fun equals(other: Any?): Boolean {
            return id == (other as Persson).id
        }
    }

    @Test
    fun `test uniqe list`(){
        val list = mutableListOf<Int>(0,0,0,0,0,0,1,0,1,1,5,1,5,4,4,4)
        var resultList = list.uniqueList()
        assertThat(resultList.count { it == 0 } == 1).isTrue()
        assertThat(resultList.count { it == 1 } == 1).isTrue()
        assertThat(resultList.count { it == 5 } == 1).isTrue()
        assertThat(resultList.count { it == 4 } == 1).isTrue()
    }


    @Test
    fun `test uniqe list with object shold be field`(){
        val list = mutableListOf<Persson>(
            Persson(0,"sdfsdfs",12),
            Persson(2,"sdfsgfgd",12),
            Persson(1,"dfgdfg",12),
            Persson(3,"sdfgdfg",12),
            Persson(4,"dfgdfg",12),
            Persson(2,"dfgdfg",12),
            Persson(0,"dfgdf",12),
        )
        var resultList = list.uniqueList()
        assertThat(resultList.count{it.id == 0} == 1).isTrue()
    }

    @Test
    fun `test uniqe list with object shold be success`(){
        val list = mutableListOf<Persson>(
            Persson(0,"sdfsdfs",12),
            Persson(2,"sdfsgfgd",12),
            Persson(1,"dfgdfg",12),
            Persson(3,"sdfgdfg",12),
            Persson(4,"dfgdfg",12),
            Persson(2,"dfgdfg",12),
            Persson(0,"dfgdf",12),
        )
        var resultList = list.uniqueList()
        assertThat(resultList.count{it.id == 0} == 1).isTrue()
    }

    @Test
    fun testValidationEmail_emailValid(){
        var email = "zakaria@gmail.com"
        assertThat(email.isValidEmail()).isTrue()
    }

    @Test
    fun testValidationEmail_emailNoValid(){
        var email = "zakaria@gmai321l.com"
        assertThat(email.isValidEmail()).isFalse()
    }
}