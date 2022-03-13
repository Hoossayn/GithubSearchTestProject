package com.example.github_ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
@ExperimentalCoroutinesApi
class SearchFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }


    @Test
    fun should_show_initial_state() {
        launchFragmentInHiltContainer<SearchFragment>()
        onView(withId(R.id.search_github_edit_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.header)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun should_show_data_when_search_is_performed() {
        launchFragmentInHiltContainer<SearchFragment>()
        onView(withId(R.id.search_github_edit_text)).perform(ViewActions.typeText("pawnjester"))
        onView(withId(R.id.rv_github_users)).check(RecyclerViewItemCountAssertion(1));
    }

}