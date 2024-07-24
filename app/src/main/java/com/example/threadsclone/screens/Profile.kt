package com.example.threadsclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadsclone.item_view.ThreadItem
import com.example.threadsclone.model.UserModel
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.util.SharedPref
import com.example.threadsclone.viewmodel.AuthViewModel
import com.example.threadsclone.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(navController: NavHostController, modifier: Modifier = Modifier, profileViewModel: ProfileViewModel= viewModel(), authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    val firebaseUser by authViewModel.firebaseUser.collectAsState()
    val threads by profileViewModel.threads.collectAsState()

    val followerList by profileViewModel.followersList.collectAsState()
    val followingList by profileViewModel.followingList.collectAsState()
    var currentUserId = ""
    if (FirebaseAuth.getInstance().currentUser != null) {
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    }

    if(currentUserId!="") {
        profileViewModel.getFollowers(currentUserId)
        profileViewModel.getFollowing(currentUserId)
    }

    val user = UserModel(
        name = SharedPref.getName(context),
        username = SharedPref.getUserName(context),
        bio = SharedPref.getBio(context),
        toString = SharedPref.getImageUrl(context)
    )
    Firebase.auth.currentUser?.let { profileViewModel.fetchThreads(it.uid) }

    LaunchedEffect(firebaseUser) {
        if(firebaseUser==null){
            navController.navigate(NavRoutes.Login.route){
                popUpTo(navController.graph.startDestinationId){
                    inclusive=true
                }
                launchSingleTop=true
            }
        }
    }
    LazyColumn(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Box(modifier = modifier
                .padding(20.dp)
                .fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Text(
                        text = SharedPref.getName(context),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(text = SharedPref.getUserName(context), fontWeight = FontWeight.Bold)
                    Text(text = SharedPref.getBio(context))
                    Row {
                        Text(text = "${followerList.size} Followers")
                        Text(text = "${followingList.size} Following")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(modifier = Modifier.clickable {
                                authViewModel.logout()
                            }, text = "LogOut")
                    }
                }
                Image(
                    painter = rememberAsyncImagePainter(model = user.toString),
                    contentDescription = "dp",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(5.dp)
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable {

                        },
                    contentScale = ContentScale.Crop
                )

            }
        }
        items(threads){pair->
            ThreadItem(thread = pair, user = user)
        }
    }


}