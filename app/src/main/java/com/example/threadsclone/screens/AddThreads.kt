package com.example.threadsclone.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadsclone.R
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.ui.theme.txtColor
import com.example.threadsclone.ui.theme.txtHintColor
import com.example.threadsclone.util.SharedPref
import com.example.threadsclone.viewmodel.AddThreadViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AddThreadsScreen(navController: NavHostController,modifier: Modifier = Modifier,addThreadViewModel: AddThreadViewModel= viewModel()) {
    var thread by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val isPosted by addThreadViewModel.isPosted.collectAsState()
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {

        }

    LaunchedEffect(key1 = isPosted) {
        if(isPosted){
            thread=""
            imageUri=null
            Toast.makeText(context,"Thread posted", Toast.LENGTH_SHORT).show()
            navController.navigate(NavRoutes.Home.route) {
                popUpTo(NavRoutes.AddThreads.route){
                    inclusive=true
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(modifier = Modifier.fillMaxWidth(.95f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.AddThreads.route){
                            inclusive=true
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Rounded.Clear, contentDescription = "cancel")
                }
                Text(
                    modifier = Modifier,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = "New Thread",color = txtColor
                )
            }
            Row(modifier=Modifier.padding(top = 10.dp)) {
                Image(
                    painter = if(SharedPref.getImageUrl(context)=="") painterResource(id = R.drawable.baseline_account_circle_24) else
                        rememberAsyncImagePainter(model = SharedPref.getImageUrl(context))
                    , contentDescription = "person image",
                    modifier = Modifier
                        .align(Alignment.Top)
                        .size(45.dp)
                        .clip(CircleShape)
                        .clickable {

                        },
                    contentScale = ContentScale.Crop
                )
            Column(modifier = Modifier.padding(start = 0.dp)) {
                Box(modifier = Modifier.padding(start=15.dp, bottom = 30.dp)) {
                    if (thread.isEmpty())
                        Text(
                        modifier = Modifier.align(Alignment.Center),
                        color = txtHintColor,
                        text = "Start a thread ..."
                    )
                    BasicTextField(
                        modifier = modifier.padding(top = 3.dp),
                        value = thread,
                        onValueChange = { thread = it },cursorBrush = SolidColor(txtColor), textStyle = TextStyle.Default.copy(color = txtColor, fontSize = 18.sp))
                }
                if(imageUri==null) {
                    IconButton(onClick = {
                        val isGranted = ContextCompat.checkSelfPermission(
                            context, permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permissionToRequest)
                        }

                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_attach_file_24),
                            contentDescription = ""
                        )
                    }
                }else{
                    Box {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUri),
                            contentDescription = "upload image", contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(10.dp))
                        )
                        IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = {
                            imageUri=null
                        }) {
                            Icon(modifier = Modifier.background(Color.Black),imageVector = Icons.Rounded.Clear,tint = Color.White, contentDescription = "cancel")
                        }
                    }
                }

            }
            }


        }
        FloatingActionButton(modifier= Modifier.padding(20.dp).align(Alignment.BottomEnd),onClick = { /*TODO*/ }) {
            TextButton(onClick = {
                if(imageUri==null){
                    addThreadViewModel.saveData(thread,FirebaseAuth.getInstance().currentUser!!.uid,"")
                }else{
                    addThreadViewModel.saveImage(thread, imageUri!!,FirebaseAuth.getInstance().currentUser!!.uid)
                }
            }) {
                Text(
                    modifier = Modifier,
                    color = txtColor,
                    text = "Post", fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}