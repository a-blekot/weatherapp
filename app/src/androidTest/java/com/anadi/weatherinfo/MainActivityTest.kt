package com.anadi.weatherinfo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anadi.weatherinfo.view.ui.mainactivity.MainActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.AutocompleteActivity
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() = Intents.init()

        @AfterClass
        @JvmStatic
        fun cleanupTests() = Intents.release()
    }

    @Test
    fun checkNavigateToAutocompleteActivity() {
//        onView(withId(R.id.layout))
//                .check(matches(isDisplayed()))
//        onView(withId(R.id.recycler_view))
//                .check(matches(isDisplayed()))
        onView(withId(R.id.add_location_button))
                .check(matches(isDisplayed()))

        onView(withId(R.id.add_location_button))
                .perform(click())

        assert(Places.isInitialized()) {
            "Places.isInitialized()"
        }

        intended(hasComponent(AutocompleteActivity::class.java.name))

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