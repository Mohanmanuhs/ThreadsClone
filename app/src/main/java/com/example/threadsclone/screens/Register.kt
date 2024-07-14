package com.example.threadsclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.threadsclone.R
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.ui.theme.ThreadsCloneTheme

@Composable
fun RegisterScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register")
        Spacer(modifier = Modifier.height(30.dp))
        Image(painter = painterResource(id = R.drawable.person), contentDescription ="person image"
        ,modifier = Modifier.size(95.dp).clip(CircleShape).clickable {

            }, contentScale = ContentScale.Crop
        )
        OutlinedTextField(value = name, modifier = modifier.fillMaxWidth(),
            onValueChange = { name = it },
            label = { Text("Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(value = username, modifier = modifier.fillMaxWidth(),
            onValueChange = { username= it },
            label = { Text("username") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(value = bio, modifier = modifier.fillMaxWidth(),
            onValueChange = { bio = it },
            label = { Text("Enter your bio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(value = email, modifier = modifier.fillMaxWidth(),
            onValueChange = { email = it },
            label = { Text("Enter your email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(value = password,
            onValueChange = { password = it }, modifier = modifier.fillMaxWidth(),
            label = { Text("Enter your password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(10.dp))
        ElevatedButton(onClick = { /*TODO*/ }) {
            Text(text = "Register", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        }
        TextButton(onClick = {


            navController.navigate(NavRoutes.Login.route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }) {
            Text(text = "Already registered ? Login Here")
        }
    }
}
@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    ThreadsCloneTheme {
        //RegisterScreen()
    }
}