package com.example.threadsclone.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadsclone.R
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.ui.components.MyImage
import com.example.threadsclone.ui.components.MyOutlinedTextField
import com.example.threadsclone.ui.theme.ThreadsCloneTheme
import com.example.threadsclone.ui.theme.backgroundColor
import com.example.threadsclone.ui.theme.txtColor
import com.example.threadsclone.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val firebaseUser by authViewModel.firebaseUser.collectAsState()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri

        }
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {

        }
    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            navController.navigate(NavRoutes.BottomNav.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyImage(
                painter = if (imageUri == null) painterResource(id = R.drawable.man) else rememberAsyncImagePainter(
                    model = imageUri
                ),
                cd = "person image",
                size = 95.dp,
                contentScale = ContentScale.Crop,
                shape = CircleShape,
                onClick = {
                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        launcher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }
                },
                modifier = modifier
            )
            Text(modifier = Modifier.padding(top = 5.dp),text = "Sign Up", fontWeight = FontWeight.SemiBold, fontSize = 28.sp,color=txtColor)

            MyOutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                modifier = modifier.fillMaxWidth(),
                innerTextColor = txtColor,
                shape = RoundedCornerShape(14.dp),
                keyboardType = KeyboardType.Text,
            )
            MyOutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                modifier = modifier.fillMaxWidth(),
                innerTextColor = txtColor,
                shape = RoundedCornerShape(14.dp),
                keyboardType = KeyboardType.Text,
            )
            MyOutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = "Bio",
                modifier = modifier.fillMaxWidth(),
                innerTextColor = txtColor,
                shape = RoundedCornerShape(14.dp),
                keyboardType = KeyboardType.Text,
            )
            MyOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                modifier = modifier.fillMaxWidth(),
                innerTextColor = txtColor,
                shape = RoundedCornerShape(14.dp),
                keyboardType = KeyboardType.Email,
            )
            MyOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                modifier = modifier.fillMaxWidth(),
                innerTextColor = txtColor,
                shape = RoundedCornerShape(14.dp),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(5.dp))
            ElevatedButton(
                onClick = {

                    if (name.isEmpty() || password.isEmpty() || username.isEmpty() || bio.isEmpty() || email.isEmpty() || imageUri == null) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.register(
                            email, password, name, username, bio, imageUri!!, context
                        )
                    }

                },
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = Color(0xFFDA5D5D), contentColor = Color(0xFFFFFFFF))
            ) {
                Text(
                    text = "Sign Up",
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp, color =Color(0xFFFCF2F2)
                )
            }
            TextButton(onClick = {
                navController.navigate(NavRoutes.Login.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }) {
                Text(text = "Already have an Account? Login Here",color = Color(0xFF0D83FF))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    ThreadsCloneTheme {
        //RegisterScreen(navController = )
    }
}