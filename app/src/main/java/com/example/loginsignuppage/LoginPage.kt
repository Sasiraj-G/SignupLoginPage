package com.example.loginsignuppage

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginPage(modifier: Modifier = Modifier,navController: NavController,authViewModel: AuthViewModel) {
    var email by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember{
        mutableStateOf(false)
    }
    var icon=if(passwordVisibility){
        painterResource(id = R.drawable.eye_open_icon)
    }else{
        painterResource(id = R.drawable._eye_hidden_icon)
        
    }
    var authState=authViewModel.authState.observeAsState()
    var context= LocalContext.current
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }

    }
    Column(
        modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Login Page", fontSize = 35.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
            email=it
        },
            label = {
                Text(text = "Email")
            })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = pass ,
            onValueChange ={
                pass=it
            }, placeholder = { Text(text = "Password")},
            label = { Text(text = "Password")},
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "visibility icon",
                        Modifier.size(28.dp)
                    )

                }
            }, visualTransformation = if(passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
            )
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            authViewModel.login(email,pass)
        }, enabled = authState.value != AuthState.Loading) {
            Text(text = "Login")

        }
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = {
            navController.navigate("signup")
        }) {
            Text(text = "Don't have an account, SignUp")

        }
    }

}

