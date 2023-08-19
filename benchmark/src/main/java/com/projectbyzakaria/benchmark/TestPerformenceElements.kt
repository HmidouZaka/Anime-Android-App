package com.projectbyzakaria.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until


fun MacrobenchmarkScope.testNavigationByNavigationBottom(){
    var home = device.findObject(By.res("home"))
    var profile = device.findObject(By.res("profile"))
    var favorite = device.findObject(By.res("favorite"))
    var search = device.findObject(By.res("search"))

    for (i in 0..5){
        search.click()
        profile.click()
        favorite.click()
        home.click()
    }


}




