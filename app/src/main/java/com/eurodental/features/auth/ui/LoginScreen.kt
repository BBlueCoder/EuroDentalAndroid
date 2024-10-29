package com.eurodental.features.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navHostController: NavHostController,viewmodel : LoginViewModel = hiltViewModel()) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val authState by viewmodel.uiState.collectAsStateWithLifecycle()
    if(authState.isUserLoggedIn) {
        LaunchedEffect(key1 = snackBarHostState) {
            snackBarHostState.showSnackbar(
                "User is already logged in!"
            )
        }
    }

    if(authState.isError) {
        LaunchedEffect(key1 = snackBarHostState) {
            snackBarHostState.showSnackbar(
                authState.errorMessage?: "Error occurred!"
            )
        }
    }

    if(authState.isLoginSuccess) {
        LaunchedEffect(key1 = snackBarHostState) {
            snackBarHostState.showSnackbar(
                "Login with success!"
            )
        }
    }

    LoginUI(
        snackBarHostState=snackBarHostState,
        emailValue = viewmodel.credentials.value.email,
        passwordValue = viewmodel.credentials.value.password,
        isLoading = authState.isLoading,
        updateEmail = {
            viewmodel.updateCredentials(it,null)
        },
        updatePassword = {
            viewmodel.updateCredentials(null,it)
        },
        onLoginClick = {
            viewmodel.login()
        }
    )
}

@Composable
fun LoginUI(
    snackBarHostState: SnackbarHostState,
    emailValue: String,
    passwordValue: String,
    isLoading: Boolean = false,
    updateEmail: (email: String) -> Unit,
    updatePassword: (password: String) -> Unit,
    onLoginClick: () -> Unit
) {

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Login")

                Spacer(modifier = Modifier.height(50.dp))

                OutlinedTextField(value = emailValue, onValueChange = {
                    updateEmail(it)
                }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = passwordValue,
                    onValueChange = {
                        updatePassword(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Bottom),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        ElevatedButton(onClick = {
                            onLoginClick()
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Login")
                        }
                    }
                }
            }
        }
    }

}

@Preview(name = "Prev")
@Composable
private fun LoginScreenPrev() {
//    LoginScreen()
}