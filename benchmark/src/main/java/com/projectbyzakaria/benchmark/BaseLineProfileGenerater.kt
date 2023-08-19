package com.projectbyzakaria.benchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalBaselineProfilesApi::class)
@RunWith(AndroidJUnit4::class)
class BaseLineProfileGenerater {

    @get:Rule
    val baseline  = BaselineProfileRule()


    @Test
    fun startUp() = baseline.collectBaselineProfile(
        packageName = "com.projectbyzakaria.animes"
    ){
        pressHome()
        startActivityAndWait()
        testNavigationByNavigationBottom()
    }



}