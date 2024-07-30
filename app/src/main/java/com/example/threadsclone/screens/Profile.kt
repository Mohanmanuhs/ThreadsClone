package com.example.threadsclone.screens

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
import androidx.compose.ui.graphics.Color
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
import com.example.threadsclone.ui.theme.backgroundColor
import com.example.threadsclone.ui.theme.rowBgColor
import com.example.threadsclone.ui.theme.txtColor
import com.example.threadsclone.ui.theme.txtHintColor
import com.example.threadsclone.util.SharedPref
import com.example.threadsclone.viewmodel.AuthViewModel
import com.example.threadsclone.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val firebaseUser by authViewModel.firebaseUser.collectAsState()
    val threads by profileViewModel.threads.collectAsState()

    val followerList by profileViewModel.followersList.collectAsState()
    val followingList by profileViewModel.followingList.collectAsState()
    var currentUserId = ""
    if (FirebaseAuth.getInstance().currentUser != null) {
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    }

    if (currentUserId != "") {
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
        if (firebaseUser == null) {
            navController.navigate(NavRoutes.Login.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    LazyColumn(
        modifier = modifier.fillMaxSize().background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(rowBgColor)
                    .padding(20.dp)
            ) {
                Column(modifier = Modifier.align(Alignment.CenterStart)) {
                    Text(
                        text = SharedPref.getName(context),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp, color = txtColor
                    )
                    Text(text = SharedPref.getUserName(context),color = txtColor)
                    Text(text = SharedPref.getBio(context), color = txtHintColor)
                    Text(text = "${followerList.size} Followers", color = txtColor)
                    Text(text = "${followingList.size} Following", color = txtColor)
                    TextButton(onClick = {
                        authViewModel.logout()
                    }) {
                        Text(modifier = Modifier, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, text = "LogOut",color = Color(0xFF3B96F3))
                    }
                }
                Image(
                    painter = rememberAsyncImagePainter(model = user.toString),
                    contentDescription = "dp",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(5.dp)
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable {

                        },
                    contentScale = ContentScale.Crop
                )
            }
            Row(modifier = Modifier.padding(top = 15.dp, bottom = 15.dp).fillMaxWidth(.9f)) {
                Text(text = "My Posts :-", color = txtColor)
            }
        }
        items(threads) { pair ->
            ThreadItem({},thread = pair, user = user, isFollow = false)
        }
    }


}