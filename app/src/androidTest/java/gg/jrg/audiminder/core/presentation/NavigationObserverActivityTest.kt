package gg.jrg.audiminder.core.presentation

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import gg.jrg.audiminder.NavigationObserverTestActivity
import gg.jrg.audiminder.R
import gg.jrg.audiminder.TestViewModel
import gg.jrg.audiminder.util.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationObserverActivityTest {

    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private val navController = Mockito.mock(NavController::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun testNavigationObserver_navigateTo() =
        ActivityScenario.launch(NavigationObserverTestActivity::class.java).use {
            dataBindingIdlingResource.monitorActivity(it)
            Navigation.setViewNavController(
                (it.getActivityReference() as NavigationObserverTestActivity).binding.navHostTestFragment,
                navController
            )

            onView(withId(R.id.bottom_navigate_to_button))
                .check(matches(isDisplayed()))
                .perform(click())

            Mockito.verify(navController).navigate(
                TestViewModel.expectedDirections
            )
        }

    @Test
    fun testNavigationObserver_navigateUp(): Unit =
        ActivityScenario.launch(NavigationObserverTestActivity::class.java).use {
            dataBindingIdlingResource.monitorActivity(it)
            Navigation.setViewNavController(
                (it.getActivityReference() as NavigationObserverTestActivity).binding.root,
                navController
            )

            onView(withId(R.id.bottom_navigate_up_button))
                .check(matches(isDisplayed()))
                .perform(click())

            Mockito.verify(navController).navigateUp()
        }

    @Test
    fun testNavigationObserver_navigateBack(): Unit =
        ActivityScenario.launch(NavigationObserverTestActivity::class.java).use {
            dataBindingIdlingResource.monitorActivity(it)
            Navigation.setViewNavController(
                (it.getActivityReference() as NavigationObserverTestActivity).binding.root,
                navController
            )

            onView(withId(R.id.bottom_navigate_back_button))
                .check(matches(isDisplayed()))
                .perform(click())

            Mockito.verify(navController).popBackStack()
        }

    @Test
    fun testNavigationObserver_navigateBackToRoot(): Unit =
        ActivityScenario.launch(NavigationObserverTestActivity::class.java).use {
            dataBindingIdlingResource.monitorActivity(it)
            Navigation.setViewNavController(
                (it.getActivityReference() as NavigationObserverTestActivity).binding.root,
                navController
            )

            onView(withId(R.id.bottom_navigate_back_to_root_button))
                .check(matches(isDisplayed()))
                .perform(click())

            Mockito.verify(navController).popBackStack(TestViewModel.expectedDestinationId, false)
        }

}

