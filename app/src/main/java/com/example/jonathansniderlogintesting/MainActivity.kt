package com.example.jonathansniderlogintesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jonathansniderlogintesting.layout.HomeScreen
import com.example.jonathansniderlogintesting.layout.LoginScreen
import com.example.jonathansniderlogintesting.layout.RegisterScreen
import com.example.jonathansniderlogintesting.ui.theme.JonathanSniderLoginTestingTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setting the content for the home page
        auth = Firebase.auth

        setContent {
            JonathanSniderLoginTestingTheme {
                //creating (and remembering) the navController
                //STATE HOIST THIS (HOWEVER THAT WORKS)
                val navController = rememberNavController()

                val loginAuth by remember { mutableStateOf(auth) }
                NavHost(
                    navController = navController,
                    startDestination = LoginScreenRoute,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                ) {
                    //these K classes can be seen as routes and with the composable
                    //the applications will start at the one given as the start destination above,
                    // and by using the 'navController.navigate' button and giving a route as
                    //an argument, its possible to move in between screens
                    composable<LoginScreenRoute> {

                        LoginScreen(loginAuth,
                            navigateToRegister = { navController.navigate(RegisterScreenRoute) },
                            navigateToHomeScreen = {navController.navigate(HomeScreenRoute)}
                            )
                    }
                    /*composable<ScreenB> {
                        val args=it.toRoute<ScreenB>()
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                Text(text="welcome to screen B, ${args.name}, ${args.age} years old")
                        }
                    }*/
                    composable<HomeScreenRoute> {
                        HomeScreen()

                    }
                    composable<RegisterScreenRoute> {
                        RegisterScreen(loginAuth,
                            navigateToLogin = {navController.navigate(LoginScreenRoute)})
                    }
                }
            }
        }
    }
}

//think of these as ROUTES:
//if data needs to be passed through the route, declare them as data classes instead
//data sent this way should be lightweight and consist of a pointer to get large resources as opposed
//to resources themselves (such as an id that identifies a large object or what to pull for an
//API call)
@Serializable
object LoginScreenRoute

@Serializable
data class ScreenB(
    val name: String?,
    val age: Int
)

@Serializable
object RegisterScreenRoute

@Serializable
object HomeScreenRoute


