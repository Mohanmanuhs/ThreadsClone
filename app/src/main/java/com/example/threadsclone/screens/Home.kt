package com.example.threadsclone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.threadsclone.item_view.ThreadItem
import com.example.threadsclone.ui.theme.backgroundColor
import com.example.threadsclone.viewmodel.HomeViewModel
import com.example.threadsclone.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(modifier: Modifier = Modifier,profileViewModel: ProfileViewModel = viewModel(),homeViewModel: HomeViewModel= viewModel()) {
    val threads by homeViewModel.threads.collectAsState()
    var currentUserId = ""
    if (FirebaseAuth.getInstance().currentUser != null) {
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    }
    val followingList by profileViewModel.followingList.collectAsState()
    profileViewModel.getFollowing(currentUserId)

    val onClick:(String)->Unit = {
        if(currentUserId!="") {
            profileViewModel.followUsers(it, currentUserId)
        }
    }
    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .background(backgroundColor), horizontalAlignment = Alignment.CenterHorizontally) {
        items(threads){
            Spacer(modifier = Modifier.height(0.dp))
            ThreadItem(onClick=onClick,it.first,it.second, isFollow = true, isFollowing = followingList.contains(it.first.userId))
        }
    }
}