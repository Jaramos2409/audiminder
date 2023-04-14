package gg.jrg.spotiminder.core.presentation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import gg.jrg.spotiminder.NavigationObserverTestFragment
import gg.jrg.spotiminder.R
import gg.jrg.spotiminder.TestViewModel
import gg.jrg.spotiminder.util.DataBindingIdlingResource
import gg.jrg.spotiminder.util.launchFragmentInHiltContainer
import gg.jrg.spotiminder.util.monitorFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationObserverTest {

    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private val navController = Mockito.mock(NavController::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
        launchFragmentInHiltContainer<NavigationObserverTestFragment>(Bundle(), R.style.AppTheme) {
            dataBindingIdlingResource.monitorFragment(this)
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun testNavigationObserver_navigateTo() {
        onView(withId(R.id.navigate_to_button))
            .check(matches(isDisplayed()))
            .perform(click())

        Mockito.verify(navController).navigate(
            TestViewModel.expectedDirections
        )
    }

    @Test
    fun testNavigationObserver_navigateUp() {
        onView(withId(R.id.navigate_up_button))
            .check(matches(isDisplayed()))
            .perform(click())

        Mockito.verify(navController).navigateUp()
    }

    @Test
    fun testNavigationObserver_navigateBack() {
        onView(withId(R.id.navigate_back_button))
            .check(matches(isDisplayed()))
            .perform(click())

        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testNavigationObserver_navigateBackToRoot() {
        onView(withId(R.id.navigate_back_to_root_button))
            .check(matches(isDisplayed()))
            .perform(click())

        Mockito.verify(navController).popBackStack(TestViewModel.expectedDestinationId, false)
    }

}

