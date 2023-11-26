package io.lb.lbtasks.sign_in.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.lb.lbtasks.LbAndroidTest
import io.lb.lbtasks.core.presentation.MainActivity
import io.lb.lbtasks.core.util.pretendToShowAToast
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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

    @Test
    fun insertingNoEmail_showsToast() = runBlocking {
        mockkStatic(::pretendToShowAToast)
        composeRule.awaitIdle()

        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToast("Write a valid email")
        }
    }

    @Test
    fun insertingAWrongEmail_showsToast() = runBlocking {
        mockkStatic(::pretendToShowAToast)
        composeRule.awaitIdle()

        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .inputEmail("jetpack-compose.com")
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToast("Write a valid email")
        }
    }

    @Test
    fun insertingNoPassword_showsToast() = runBlocking {
        mockkStatic(::pretendToShowAToast)
        composeRule.awaitIdle()

        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .inputEmail("jetpack@compose.com")
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToast("Write your password")
        }
    }

    @Test
    fun insertingOnePasswordOnly_showsToast() = runBlocking {
        mockkStatic(::pretendToShowAToast)
        composeRule.awaitIdle()

        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .inputEmail("jetpack@compose.com")
            .inputPassword("jetpackPassword")
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToast("The passwords don't match")
        }
    }

    @Test
    fun insertingDifferentPasswords_showsToast() = runBlocking {
        mockkStatic(::pretendToShowAToast)
        composeRule.awaitIdle()

        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .inputEmail("jetpack@compose.com")
            .inputPassword("jetpackPassword")
            .inputRepeatedPassword("differentPassword")
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToast("The passwords don't match")
        }
    }
}
