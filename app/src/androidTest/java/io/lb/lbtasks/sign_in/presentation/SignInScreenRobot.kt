package io.lb.lbtasks.sign_in.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import io.lb.lbtasks.core.util.LBComposeRule

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class SignInScreenRobot(
    private val composeRule: LBComposeRule
) {
    fun clickHomeSignIn(): SignInScreenRobot {
        composeRule.onAllNodes(
            hasText("Sign in")
                .and(hasClickAction())
        ).onFirst()
            .assertIsDisplayed()
            .performClick()

        composeRule.waitForIdle()
        return this
    }

    fun clickHomeLogin(): SignInScreenRobot {
        composeRule.onAllNodes(
            hasText("Login")
                .and(hasClickAction())
        ).onFirst()
            .assertIsDisplayed()
            .performClick()

        composeRule.waitForIdle()
        return this
    }

    fun clickBottomSheetSignIn(): SignInScreenRobot {
        composeRule.onAllNodes(
            hasText("Sign in")
                .and(hasClickAction())
        ).onLast()
            .assertIsDisplayed()
            .performClick()

        composeRule.waitForIdle()
        return this
    }

    fun clickBottomSheetLogin(): SignInScreenRobot {
        composeRule.onAllNodes(
            hasText("Login")
                .and(hasClickAction())
        ).onLast()
            .assertIsDisplayed()
            .performClick()

        composeRule.waitForIdle()
        return this
    }

    fun inputEmail(email: String): SignInScreenRobot {
        composeRule.onNode(
            hasText("E-mail")
        ).assertIsDisplayed()
            .performTextInput(email)
        return this
    }

    fun inputPassword(password: String): SignInScreenRobot {
        composeRule.onNode(
            hasText("Password")
        ).assertIsDisplayed()
            .performTextInput(password)
        return this
    }

    fun inputRepeatedPassword(password: String): SignInScreenRobot {
        composeRule.onNode(
            hasText("Repeat your password")
        ).assertIsDisplayed()
            .performTextInput(password)
        return this
    }
}
