package com.example.jonathansniderlogintesting.layout

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.example.jonathansniderlogintesting.MainActivity
import com.example.jonathansniderlogintesting.MoveToRegisterScreen
import com.example.jonathansniderlogintesting.R
import com.example.jonathansniderlogintesting.ScreenB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//@Preview(showBackground = true)
@Composable
fun LoginScreen(
    auth: FirebaseAuth,
    navController: NavController
) {
    val context = LocalContext.current
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.teal_200))
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(
            ) {
                //email field
                var emailText by remember { mutableStateOf("") }
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = null
                        )
                    },
                    value = emailText,
                    onValueChange = { emailText = it },
                    label = { Text(stringResource(R.string.email_text_field)) },
                    modifier = Modifier.padding(10.dp)

                )
                //password field
                var passwordText by remember { mutableStateOf("") }
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    value = passwordText,
                    onValueChange = { passwordText = it },
                    label = { Text(stringResource(R.string.password_text_field)) },
                    modifier = Modifier.padding(10.dp)

                )
                //login button, move to home screen after SUCCESSFUL login
                Button(
                    onClick = {
                        verifyFirebaseUser(emailText, passwordText, auth, context, navController)
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(R.string.login_button))
                }

                //register button, move to register screen
                Button(
                    onClick = {
                        Toast.makeText(context, "hit the register button", Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate(
                            ScreenB(
                                name = "Billy",
                                age = 25
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(R.string.register_button))
                }
            }
        }
    }
}

private fun verifyFirebaseUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    context: Context,
    navController: NavController
) {
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val user = auth.currentUser
            Toast.makeText(context, "Good Login by ${user?.email}", Toast.LENGTH_LONG).show()
            navController.navigate(
                MoveToRegisterScreen
            )

            //navigate to Home Page
        } else {
            Toast.makeText(context, "Bad Login: ${task.exception?.message}", Toast.LENGTH_LONG)
                .show()
        }
    }
}







