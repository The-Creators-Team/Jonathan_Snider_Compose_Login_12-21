package com.example.jonathansniderlogintesting

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jonathansniderlogintesting.layout.HomeScreen
import com.example.jonathansniderlogintesting.layout.LoginScreen
import com.example.jonathansniderlogintesting.layout.RegisterScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class LoginScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    val auth = Firebase.auth

    @Test
    fun loginScreen_successfulStart() {
        //setting up mock values to put into the email and password fields
        val email = "test@example.com"
        val password = "testPassword"
        //setting up a basic version of the home page and navcontroller
        composeTestRule.setContent {
            val navController = rememberNavController()
            val loginAuth by remember { mutableStateOf(auth) }
            NavHost(
                navController = navController,
                startDestination = LoginScreenRoute,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                composable<LoginScreenRoute> {
                    LoginScreen(loginAuth,
                        navigateToRegister = { navController.navigate(RegisterScreenRoute) },
                        navigateToHomeScreen = { navController.navigate(HomeScreenRoute) }
                    )
                }
            }
        }

        // Find the email TextField and perform text input
        composeTestRule.onNodeWithText("email", ignoreCase = true)
            .performTextInput(email)
        // Find the password TextField and perform text input
        composeTestRule.onNodeWithText("password", ignoreCase = true)
            .performTextInput(password)


        //these tests simply check if the compose test was able to find the TextFields listed above
        // and type text into them, indicating we're on the login page
        assertTrue(email.isNotEmpty())
        assertTrue(password.isNotEmpty())
    }

    @Test
    fun loginScreen_emptyText() {
        //setting up mock values to put into the email and password fields
        val email = "test@example.com"
        val password = ""
        //setting up a basic version of the home page and navcontroller
        composeTestRule.setContent {
            val navController = rememberNavController()
            val loginAuth by remember { mutableStateOf(auth) }
            NavHost(
                navController = navController,
                startDestination = LoginScreenRoute,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                composable<LoginScreenRoute> {
                    LoginScreen(loginAuth,
                        navigateToRegister = { navController.navigate(RegisterScreenRoute) },
                        navigateToHomeScreen = { navController.navigate(HomeScreenRoute) }
                    )
                }
            }
        }

        // Find the email TextField and perform text input
        composeTestRule.onNodeWithText("email", ignoreCase = true)
            .performTextInput(email)
        // Find the password TextField and perform text input
        composeTestRule.onNodeWithText("password", ignoreCase = true)
            .performTextInput(password)


        //check if the login button is enabled. It should NOT be because one field
        //is missing text
        composeTestRule.onNodeWithText("login", ignoreCase = true).assertIsNotEnabled()
    }

    @Test
    fun loginScreen_successfulNavigateRegister() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val loginAuth by remember { mutableStateOf(auth) }
            NavHost(
                navController = navController,
                startDestination = LoginScreenRoute,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                composable<LoginScreenRoute> {

                    LoginScreen(loginAuth,
                        navigateToRegister = { navController.navigate(RegisterScreenRoute) },
                        navigateToHomeScreen = { navController.navigate(HomeScreenRoute) }
                    )
                }

                composable<RegisterScreenRoute> {
                    RegisterScreen(loginAuth,
                        navigateToLogin = { navController.navigate(LoginScreenRoute) })
                }
            }
        }

        //performing click on node with this text (otherwise known as the register button)
        composeTestRule.onNodeWithText("register", ignoreCase = true).performClick()

        //testing if I'm on the register screen by looking for something on it
        //(in this case the new password field)
        composeTestRule.onNodeWithText("new password", ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun loginScreen_successfulLogin() {
        val email = "test@test.com"
        val password = "helloWorld"
        composeTestRule.setContent {
            val navController = rememberNavController()
            val loginAuth by remember { mutableStateOf(auth) }
            NavHost(
                navController = navController,
                startDestination = LoginScreenRoute,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                composable<LoginScreenRoute> {

                    LoginScreen(loginAuth,
                        navigateToRegister = { navController.navigate(RegisterScreenRoute) },
                        navigateToHomeScreen = { navController.navigate(HomeScreenRoute) }
                    )
                }
                composable<HomeScreenRoute> {
                    HomeScreen()

                }
            }
        }
        // Find the email TextField and perform text input
        composeTestRule.onNodeWithText("email", ignoreCase = true)
            .performTextInput(email)
        // Find the password TextField and perform text input
        composeTestRule.onNodeWithText("password", ignoreCase = true)
            .performTextInput(password)
        //clicking on login after putting in the information above
        composeTestRule.onNodeWithText("login", ignoreCase = true).performClick()

        //wait for a bit to give authentication time to occur,
        //then test to see if we successfully navigated to the home screen page
        //(has nothing on it but a text box saying 'hello')
        composeTestRule.waitUntil(10000) {
            composeTestRule.onNodeWithText("hello", ignoreCase = true)
                .isDisplayed()
        }


    }
}