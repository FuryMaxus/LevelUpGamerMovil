package com.example.levelupmovil.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.levelupmovil.repository.AuthRepository
import com.example.levelupmovil.ui.theme.LevelUpMovilTheme
import com.example.levelupmovil.viewmodel.RegisterViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val authRepository = mockk<AuthRepository>(relaxed = true)
    private val viewModel = RegisterViewModel(authRepository)

    @Test
    fun registerForm_allowsInput_and_click() {
        composeTestRule.setContent {
            LevelUpMovilTheme {
                Surface {
                    RegisterScreen(
                        viewModel = viewModel,
                        onRegisterSuccess = {},
                        onLoginClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("reg_name").performTextInput("jose valenzuela")
        composeTestRule.onNodeWithTag("reg_email").performTextInput("jose@test.com")
        composeTestRule.onNodeWithTag("reg_address").performTextInput("definitivamente una calle")
        composeTestRule.onNodeWithTag("reg_pass").performTextInput("123456")

        composeTestRule.onNodeWithTag("reg_terms").performClick()

        composeTestRule.onNodeWithTag("reg_button")
            .assertIsDisplayed()
            .performClick()
    }
}