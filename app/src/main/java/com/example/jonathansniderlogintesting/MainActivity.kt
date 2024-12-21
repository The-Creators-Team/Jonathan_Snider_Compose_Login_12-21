package com.example.jonathansniderlogintesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jonathansniderlogintesting.layout.TestComposable
import com.example.jonathansniderlogintesting.ui.theme.JonathanSniderLoginTestingTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
            //setting the content for the home page
        setContent {
            JonathanSniderLoginTestingTheme {
                //creating (and remembering) the navController
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = StartScreen
                ) {
                    //these K classes can be seen as routes and with the composable
                    composable<StartScreen> {
                        TestComposable({
                            navController.navigate(ScreenB(
                                name ="Billy",
                                age=25
                            ))
                        })
                    }
                    composable<ScreenB> {
                        val args=it.toRoute<ScreenB>()
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                Text(text="welcome to screen B, ${args.name}, ${args.age} years old")
                        }
                    }
                }
            }
        }
    }
}

//think of these as ROUTES:
//if data needs to be passed through the route, declare them as data classes instead
@Serializable
object StartScreen

@Serializable
data class ScreenB(
    val name: String?,
    val age: Int
)
