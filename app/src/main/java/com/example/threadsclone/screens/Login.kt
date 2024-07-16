package com.example.threadsclone.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.ui.theme.ThreadsCloneTheme
import com.example.threadsclone.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavHostController, modifier: Modifier = Modifier,authViewModel: AuthViewModel= viewModel()) {
    val firebaseUser by authViewModel.firebaseUser.collectAsState()
    val error by authViewModel.error.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(firebaseUser,error) {
        if(firebaseUser!=null)
        {
            navController.navigate(NavRoutes.BottomNav.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }else if(error!=null){
            Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login")
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(value = email, modifier = modifier.fillMaxWidth(),
            onValueChange = { email = it },
            label = { Text("Enter your email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = password,
            onValueChange = { password = it }, modifier = modifier.fillMaxWidth(),
            label = { Text("Enter your password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(10.dp))
        ElevatedButton(onClick = {
            if (email.isNotEmpty() && password.isNotEmpty())
            authViewModel.login(email,password,context)
            else
                Toast.makeText(context,"Please fill all fields",Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Login", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        }
        TextButton(onClick = {

            navController.navigate(NavRoutes.Register.route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }) {
            Text(text = "New user? Create account")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    ThreadsCloneTheme {
        //LoginScreen()
    }
}