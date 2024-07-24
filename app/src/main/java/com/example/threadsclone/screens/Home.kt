package com.example.threadsclone.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.threadsclone.item_view.ThreadItem
import com.example.threadsclone.ui.theme.ThreadsCloneTheme
import com.example.threadsclone.viewmodel.HomeViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier,homeViewModel: HomeViewModel= viewModel()) {
    val threads by homeViewModel.threads.collectAsState()


    LazyColumn(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        items(threads){
            Spacer(modifier = Modifier.height(10.dp))
            ThreadItem(it.first,it.second)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPrev() {
    ThreadsCloneTheme {
        HomeScreen()
    }
}