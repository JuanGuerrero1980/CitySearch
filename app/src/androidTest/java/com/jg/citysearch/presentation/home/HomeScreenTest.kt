package com.jg.citysearch.presentation.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jg.citysearch.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun searchField_isDisplayed() {
        composeTestRule.onNodeWithTag("searchField").assertIsDisplayed()
    }

    @Test
    fun toggleFavorites_switchWorks() {
        composeTestRule.onNodeWithText("Favorites").assertIsDisplayed()
        composeTestRule.onNode(isToggleable()).performClick()
    }


}