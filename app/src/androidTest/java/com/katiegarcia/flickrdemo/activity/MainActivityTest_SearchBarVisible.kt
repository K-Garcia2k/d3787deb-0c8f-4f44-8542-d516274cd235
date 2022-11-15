package com.katiegarcia.flickrdemo.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.katiegarcia.flickrdemo.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
class MainActivityTest_SearchBarVisible {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest_SearchBarVisible() {
        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_gallery_search)
            )
        )
        actionMenuItemView.perform(click())

        val editText = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_src_text),
            )
        )
        editText.check(matches(isDisplayed()))
    }
}
