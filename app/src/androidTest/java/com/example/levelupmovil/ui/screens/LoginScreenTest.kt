package com.example.levelupmovil.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.levelupmovil.MainActivity
import com.example.levelupmovil.repository.AuthRepository
import com.example.levelupmovil.ui.theme.LevelUpMovilTheme
import com.example.levelupmovil.viewmodel.LoginViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val authRepository = mockk<AuthRepository>(relaxed = true)
    private val viewModel = LoginViewModel(authRepository)

    @Test
    fun loginScreen_showsElements_correctly() {
        composeTestRule.setContent {
            LevelUpMovilTheme {
                Surface {
                    LoginScreen(
                        viewModel = viewModel,
                        onLoginSuccess = {},
                        onRegisterClick = {}
                    )
                }
            }
        }
        composeTestRule.onNodeWithTag("email_input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("password_input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_button").assertIsDisplayed()
    }

    @Test
    fun writingInFields_enablesButton_and_clickWorks() {
        composeTestRule.setContent {
            LevelUpMovilTheme {
                Surface {
                    LoginScreen(
                        viewModel = viewModel,
                        onLoginSuccess = {},
                        onRegisterClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("email_input").performTextInput("test@test.com")
        composeTestRule.onNodeWithTag("password_input").performTextInput("123456")

        composeTestRule.onNodeWithTag("login_button").performClick()
    }
}