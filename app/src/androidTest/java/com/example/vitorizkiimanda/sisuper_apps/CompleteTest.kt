package com.example.vitorizkiimanda.sisuper_apps

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.pressBack
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.vitorizkiimanda.sisuper_apps.R.id.*
import com.example.vitorizkiimanda.sisuper_apps.activity.Onboarding
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CompleteTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(Onboarding::class.java)

    @Test
    fun testAll(){
        ////Onboarding
        Espresso.onView(ViewMatchers.withId(slideViewPager))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(slideViewPager)).perform(ViewActions.swipeLeft())
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(slideViewPager)).perform(ViewActions.swipeLeft())
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(slideViewPager)).perform(ViewActions.swipeLeft())
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(slideViewPager)).perform(ViewActions.swipeLeft())
        Thread.sleep(1500)

        Espresso.onView(ViewMatchers.withId(action_button)).perform(
                (ViewActions.click()))

        ////Login     please allow the Contact Permission First
        Thread.sleep(1500)

        Espresso.onView(ViewMatchers.withId(login_layout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(email)).perform(
                (ViewActions.typeText("vitorizkiimanda@gmail.com")))
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(password)).perform(
                (ViewActions.typeText("vitovito")))
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(email_sign_in_button)).perform(
                (ViewActions.click()))
        Thread.sleep(1500)

        ////choose business
        Espresso.onView(ViewMatchers.withId(business_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ViewActions.click()))
        Thread.sleep(1500)

        ////Produk-ku
        //create product
        Espresso.onView(ViewMatchers.withId(product_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(1500)

        Espresso.onView(ViewMatchers.withId(name_input_product)).perform(
                (ViewActions.typeText("Geprek Presentasi")))
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(price_input_product)).perform(
                (ViewActions.typeText("30000")))
        Thread.sleep(1500)
        Espresso.onView(ViewMatchers.withId(add_input_product)).perform(
                (ViewActions.click()))
        Thread.sleep(1500)
    }

}