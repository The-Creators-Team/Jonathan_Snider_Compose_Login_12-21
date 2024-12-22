package com.example.jonathansniderlogintesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jonathansniderlogintesting.layout.LoginScreen
import com.example.jonathansniderlogintesting.layout.RegisterScreen
import com.example.jonathansniderlogintesting.ui.theme.JonathanSniderLoginTestingTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.Serializable
import kotlin.math.log

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
                    startDestination = LoginScreenRoute
                ) {
                    //these K classes can be seen as routes and with the composable
                    //the applications will start at the one given as the start destination above,
                    // and by using the 'navController.navigate' button and giving a route as
                    //an argument, its possible to move in between screens
                    composable<LoginScreenRoute> {

                        LoginScreen(loginAuth, navController)
                        //LoginScreen(auth, navController)
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

                    }
                    composable<RegisterScreenRoute> {
                        RegisterScreen(loginAuth, navController)
                        //RegisterScreen(auth, navController)
                    }
                }
            }
        }
    }
}

//think of these as ROUTES:
//if data needs to be passed through the route, declare them as data classes instead
//data sent this way should be lightweight and consist of an item to get large resources as opposed
//to resources themselves (such as an id that identifies a large object or what to pull for an
//API call
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


