package com.zjp.compose_unit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.zjp.compose_unit.ui.App
import com.zjp.compose_unit.ui.home.Composes
import com.zjp.compose_unit.ui.home.ComposesScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ComposesScreen()
        }
    }

    @Test
    fun app_launches() {
        composeTestRule.onNodeWithText("HOME").assertIsDisplayed()
        composeTestRule.onNodeWithText("Android's picks").assertIsDisplayed()
    }

}