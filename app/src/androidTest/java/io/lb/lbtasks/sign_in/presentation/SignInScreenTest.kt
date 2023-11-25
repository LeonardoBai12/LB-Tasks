package io.lb.lbtasks.sign_in.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidTest
import io.lb.lbtasks.LbAndroidTest
import io.lb.lbtasks.core.presentation.MainActivity
import io.lb.lbtasks.core.util.pretendToShowAToast
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@HiltAndroidTest
class SignInScreenTest : LbAndroidTest() {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun asdasdda() = runBlocking {
        mockkStatic(::pretendToShowAToast)
        composeRule.awaitIdle()

        composeRule.onAllNodes(
            hasText("Sign in")
                .and(hasClickAction())
        ).onFirst()
            .assertIsDisplayed()
            .performClick()

        composeRule.waitForIdle()

        composeRule.onNode(
            hasText("E-mail")
        ).assertIsDisplayed()
            .performTextInput("jetpack-compose.com")

        composeRule.onAllNodes(
            hasText("Sign in")
                .and(hasClickAction())
        ).onLast()
            .assertIsDisplayed()
            .performClick()

        delay(1500)

        verify {
            pretendToShowAToast("Write a valid email")
        }
    }
}
