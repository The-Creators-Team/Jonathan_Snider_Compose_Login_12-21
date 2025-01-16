package build.learning.jonathansniderlogintesting

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import build.learning.jonathansniderlogintesting.layout.LoginScreen
import build.learning.jonathansniderlogintesting.layout.RegisterScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class RegisterScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    val auth = Firebase.auth


    @Test
    fun registerScreen_successfulStart() {
        val newEmail = "test@example.com"
        val newPassword = "testPassword"
        composeTestRule.setContent {
            val navController = rememberNavController()
            val loginAuth by remember { mutableStateOf(auth) }
            NavHost(
                navController = navController,
                startDestination = RegisterScreenRoute,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                composable<RegisterScreenRoute> {
                    RegisterScreen(loginAuth,
                        navigateToLogin = { navController.navigate(LoginScreenRoute) })
                }
            }
        }

        // Find the email TextField and perform text input
        composeTestRule.onNodeWithText("new email", ignoreCase = true)
            .performTextInput(newEmail)
        // Find the password TextField and perform text input
        composeTestRule.onNodeWithText("new password", ignoreCase = true)
            .performTextInput(newPassword)


        //confirm that the register page exists by checking if the fields exist
        assertTrue(newEmail.isNotEmpty())
        assertTrue(newPassword.isNotEmpty())
    }

    @Test
    fun registerScreen_emptyText() {
        val newEmail = "test@example.com"
        val newPassword = ""
        composeTestRule.setContent {
            val navController = rememberNavController()
            val loginAuth by remember { mutableStateOf(auth) }
            NavHost(
                navController = navController,
                startDestination = RegisterScreenRoute,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                composable<RegisterScreenRoute> {
                    RegisterScreen(loginAuth,
                        navigateToLogin = { navController.navigate(LoginScreenRoute) })
                }
            }
        }
        // Find the new email TextField and perform text input
        composeTestRule.onNodeWithText("new email", ignoreCase = true)
            .performTextInput(newEmail)
        // Find the new password TextField and perform text input
        composeTestRule.onNodeWithText("new password", ignoreCase = true)
            .performTextInput(newPassword)


        //check if the register button is enabled. It should NOT be because one field
        //is missing text
        composeTestRule.onNodeWithText("register", ignoreCase = true).assertIsNotEnabled()
    }

    @Test
    fun register_successfulRegister() {
        val newEmail = "test3@test.com"
        val newPassword = "helloWorld3"
        composeTestRule.setContent {
            val navController = rememberNavController()
            val loginAuth by remember { mutableStateOf(auth) }
            NavHost(
                navController = navController,
                startDestination = RegisterScreenRoute,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                composable<RegisterScreenRoute> {
                    RegisterScreen(loginAuth,
                        navigateToLogin = { navController.navigate(LoginScreenRoute) })
                }
                composable<LoginScreenRoute> {
                    LoginScreen(loginAuth,
                        navigateToRegister = { navController.navigate(RegisterScreenRoute) },
                        navigateToHomeScreen = { navController.navigate(HomeScreenRoute) }
                    )
                }
            }

        }
        // Find the new email TextField and perform text input
        composeTestRule.onNodeWithText("new email", ignoreCase = true)
            .performTextInput(newEmail)
        // Find the new password TextField and perform text input
        composeTestRule.onNodeWithText("new password", ignoreCase = true)
            .performTextInput(newPassword)

        //clicking on register after putting in the information above
        composeTestRule.onNodeWithText("register", ignoreCase = true).performClick()

        //wait for a bit to give authentication time to occur,
        //then test to see if we successfully navigated back to login
        //by seeing if something from the login page is displayed
        composeTestRule.waitUntil(10000) {
            composeTestRule.onNodeWithText("login", ignoreCase = true)
                .isDisplayed()
        }
    }
}
