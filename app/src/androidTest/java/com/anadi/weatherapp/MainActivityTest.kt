package com.anadi.weatherapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anadi.weatherapp.ui.UnlockScreenRule
import com.anadi.weatherapp.view.ui.mainactivity.MainActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.AutocompleteActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.endsWith
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val activityTestRuleASDASDASD = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Rule
    @JvmField
    val unlockActivityRule: RuleChain = RuleChain.outerRule(activityTestRuleASDASDASD)
            .around(UnlockScreenRule(activityTestRuleASDASDASD))

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() = Intents.init()

        @AfterClass
        @JvmStatic
        fun cleanupTests() = Intents.release()

        private const val CITY = "London"
    }

    @Test
    fun checkNavigateToAutocompleteActivity() {
        //        onView(withId(R.id.layout))
        //                .check(matches(isDisplayed()))
        //        onView(withId(R.id.recycler_view))
        //                .check(matches(isDisplayed()))
        onView(withId(R.id.add_location_button)).check(matches(isDisplayed()))

        onView(withId(R.id.add_location_button)).perform(click())

        assert(Places.isInitialized()) {
            "Places.isInitialized()"
        }

        intended(hasComponent(AutocompleteActivity::class.java.name))

        onView(allOf(withClassName(endsWith("EditText")))).perform(replaceText(CITY))

        clickXY(100, 200)

        Thread.sleep(300)

        intended(hasComponent(MainActivity::class.java.name))

        val cityNameView = onView(allOf(withId(R.id.name), withText(CITY)))

        cityNameView.check(matches(isDisplayed()))
        cityNameView.perform(click())

        //        onView(withId(R.id.name_field)).perform(typeText("Vivek Maskara"))

        //        onData(withId(R.id.item_image))
        //                .inAdapterView(withId(R.id.grid_adapter_id))
        //                .atPosition(0)
        //                .perform(click());

        //        onView(withId(R.id.name_field)).perform(typeText("Vivek Maskara"))
        //        onView(withId(R.id.set_user_name)).perform(click())
        //        onView(withText("Hello Vivek Maskara!")).check(matches(isDisplayed()))
    }
}

fun clickXY(x: Int, y: Int): ViewAction? {
    return GeneralClickAction(Tap.SINGLE, CoordinatesProvider { view ->
        val screenPos = IntArray(2)
        view.getLocationOnScreen(screenPos)
        val screenX = screenPos[0] + x.toFloat()
        val screenY = screenPos[1] + y.toFloat()
        floatArrayOf(screenX, screenY)
    }, Press.FINGER, 0, 0)
}

//fun withIndex(matcher: Matcher<View?>, index: Int): Any {
//    return object : TypeSafeMatcher<View?>() {
//        var currentIndex = 0
//        override fun describeTo(description: Description) {
//            description.appendText("with index: ")
//            description.appendValue(index)
//            matcher.describeTo(description)
//        }
//
//        override fun matchesSafely(view: View?): Boolean {
//            return matcher.matches(view) && currentIndex++ == index
//        }
//    }
//}