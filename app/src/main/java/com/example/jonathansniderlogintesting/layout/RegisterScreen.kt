package com.example.jonathansniderlogintesting.layout

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jonathansniderlogintesting.LoginScreenRoute
import com.example.jonathansniderlogintesting.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun RegisterScreen(
    auth: FirebaseAuth,
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(
            ) {
                //new email field
                var newEmailText by remember { mutableStateOf("") }
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    },
                    value = newEmailText,
                    onValueChange = { newEmailText = it },
                    label = { Text(stringResource(R.string.register_email_text_field)) },
                    modifier = Modifier.padding(10.dp)

                )
                //new password field
                var newPasswordText by remember { mutableStateOf("") }
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    },
                    value = newPasswordText,
                    onValueChange = { newPasswordText = it },
                    label = { Text(stringResource(R.string.register_password_text_field)) },
                    modifier = Modifier.padding(10.dp)

                )
                //register button, move back to login after SUCCESSFUL register
                var enabledState by remember { mutableStateOf(true) }
                if (newEmailText.isEmpty() || newPasswordText.isEmpty()) {
                    enabledState = false
                } else {
                    enabledState = true
                }
                Button(
                    onClick = {
                        createNewFirebaseUser(
                            newEmailText, newPasswordText,
                            auth = auth,
                            context = context,
                            navigateToLogin = navigateToLogin
                        )
                    },
                    enabled = enabledState,
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

private fun createNewFirebaseUser(
    newEmail: String,
    newPassword: String,
    auth: FirebaseAuth,
    context: Context,
    navigateToLogin: () -> Unit
) {
    auth.createUserWithEmailAndPassword(newEmail, newPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                Toast.makeText(context, "New Account by ${user?.email}", Toast.LENGTH_LONG)
                    .show()
                auth.signOut()
                //move back to login page
                navigateToLogin()

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    context, "Authentication failed.", Toast.LENGTH_SHORT,
                ).show()
            }
        }
}

