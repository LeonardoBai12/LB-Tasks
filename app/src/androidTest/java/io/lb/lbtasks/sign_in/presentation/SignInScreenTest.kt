package io.lb.lbtasks.sign_in.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.lb.lbtasks.LBAndroidTest
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.MainActivity
import io.lb.lbtasks.core.util.pretendToShowAToast
import io.lb.lbtasks.core.util.pretendToShowAToastWithResId
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
class SignInScreenTest : LBAndroidTest() {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    override fun setUp() {
        super.setUp()
        mockkStatic(::pretendToShowAToast)
        composeRule.waitForIdle()
    }

    @Test
    fun insertingNoEmailOnSignIn_showsToast() = runBlocking {
        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToast("Write a valid email")
        }
    }

    @Test
    fun insertingAWrongEmailOnSignIn_showsToast() = runBlocking {
        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .inputEmail("jetpack-compose.com")
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToast("Write a valid email")
        }
    }

    @Test
    fun insertingNoPasswordOnSignIn_showsToast() = runBlocking {
        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .inputEmail("jetpack@compose.com")
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToast("Write your password")
        }
    }

    @Test
    fun insertingOnePasswordOnlyOnSignIn_showsToast() = runBlocking {
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
    fun insertingDifferentPasswordsOnSignIn_showsToast() = runBlocking {
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

    @Test
    fun insertingValidDataOnSignIn_navigatesToTaskScreen() = runBlocking<Unit> {
        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .inputEmail("jetpack@compose.com")
            .inputPassword("jetpackPassword")
            .inputRepeatedPassword("jetpackPassword")
            .clickBottomSheetSignIn()

        verify {
            pretendToShowAToastWithResId(R.string.sign_in_successful)
        }
    }

    @Test
    fun insertingNoEmailOnLogin_showsToast() = runBlocking {
        SignInScreenRobot(composeRule)
            .clickHomeLogin()
            .clickBottomSheetLogin()

        verify {
            pretendToShowAToast("Write a valid email")
        }
    }

    @Test
    fun insertingAWrongEmailOnLogin_showsToast() = runBlocking {
        SignInScreenRobot(composeRule)
            .clickHomeLogin()
            .inputEmail("jetpack-compose.com")
            .clickBottomSheetLogin()

        verify {
            pretendToShowAToast("Write a valid email")
        }
    }

    @Test
    fun insertingNoPasswordOnLogin_showsToast() = runBlocking {
        SignInScreenRobot(composeRule)
            .clickHomeLogin()
            .inputEmail("jetpack@compose.com")
            .clickBottomSheetLogin()

        verify {
            pretendToShowAToast("Write your password")
        }
    }

    @Test
    fun insertingValidDataOnLogin_navigatesToTaskScreen() = runBlocking {
        SignInScreenRobot(composeRule)
            .clickHomeSignIn()
            .inputEmail("jetpack@compose.com")
            .inputPassword("jetpackPassword")
            .inputRepeatedPassword("jetpackPassword")
            .clickBottomSheetSignIn()
            .clickLogout()
            .apply { delay(500) }
            .clickHomeLogin()
            .inputEmail("jetpack@compose.com")
            .inputPassword("jetpackPassword")
            .clickBottomSheetLogin()

        verify(exactly = 2) {
            pretendToShowAToastWithResId(R.string.sign_in_successful)
        }
    }
}
