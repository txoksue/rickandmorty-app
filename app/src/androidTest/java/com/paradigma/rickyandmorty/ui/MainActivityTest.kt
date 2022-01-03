package com.paradigma.rickyandmorty.ui

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.getToolbarNavigationTitle
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Test
    fun mainActivity_clickFavoriteList(){

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        Thread.sleep(4000)

        onView(withId(R.id.menu_action_go_to_favorites)).perform(click())

        onView(withId(R.id.component_favorite_no_result)).check(matches(isDisplayed()))

        activityScenario.close()
    }


    @Test
    fun mainActivity_checkTitle(){

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        Thread.sleep(4000)

        val title = activityScenario.getToolbarNavigationTitle()

        assertEquals(title, ApplicationProvider.getApplicationContext<Context>().getString(R.string.app_name))

        activityScenario.close()
    }

}