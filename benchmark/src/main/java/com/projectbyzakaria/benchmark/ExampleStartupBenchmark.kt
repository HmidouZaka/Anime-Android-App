package com.projectbyzakaria.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()


    @Test
    fun testSetUpNoProfile() = testStatUp(CompilationMode.None())

    @Test
    fun testNavigationNoProfile() = testNavigation(CompilationMode.None())


    @Test
    fun testSetUpWithProfile() = testStatUp(CompilationMode.Partial())

    @Test
    fun testNavigationWithProfile() = testNavigation(CompilationMode.Partial())


    fun testStatUp(mode:CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.projectbyzakaria.animes",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ){
        pressHome()
        startActivityAndWait()
    }



    fun testNavigation(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.projectbyzakaria.animes",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()
        testNavigationByNavigationBottom()
    }



}