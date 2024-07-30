package com.example.threadsclone.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.ui.components.MyOutlinedTextField
import com.example.threadsclone.ui.theme.backgroundColor
import com.example.threadsclone.ui.theme.rwrFont
import com.example.threadsclone.ui.theme.txtColor
import com.example.threadsclone.ui.theme.welcomeBackColor
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

    Box(
        modifier = Modifier.background(backgroundColor)
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = modifier.fillMaxSize()
            ,verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome Back", fontFamily = rwrFont, fontSize = 35.sp, color = welcomeBackColor)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Login", fontWeight = FontWeight.SemiBold, fontSize = 28.sp, color = txtColor)
            MyOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = "Enter your email",
                modifier = modifier.fillMaxWidth(),
                innerTextColor = txtColor,
                shape = RoundedCornerShape(14.dp),
                keyboardType = KeyboardType.Email,
            )
            MyOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Enter your password",
                modifier = modifier.fillMaxWidth(),
                innerTextColor = txtColor,
                shape = RoundedCornerShape(14.dp),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(5.dp))

            ElevatedButton(onClick = {
                if (email.isNotEmpty() && password.isNotEmpty())
                authViewModel.login(email,password,context)
                else
                    Toast.makeText(context,"Please fill all fields",Toast.LENGTH_SHORT).show()
            },
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = Color(0xFFDA5D5D), contentColor = Color(0xFFFFFFFF))) {
                Text(  modifier = Modifier.padding(5.dp),text = "Login", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            TextButton(onClick = {

                navController.navigate(NavRoutes.Register.route){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop=true
                }
            }) {
                Text(text = "New user? Create account", color = Color(0xFF0D83FF)
                )
            }
        }

    }
}