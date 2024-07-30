package com.example.threadsclone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.threadsclone.item_view.UserItem
import com.example.threadsclone.ui.theme.backgroundColor
import com.example.threadsclone.viewmodel.SearchViewModel

@Composable
fun SearchScreen(navController: NavHostController,modifier: Modifier = Modifier,searchViewModel: SearchViewModel= viewModel()) {
    val userList by searchViewModel.users.collectAsState()
    var search by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = search,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            onValueChange = { search = it },
            label = { Text("Search User") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") }
        )
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val filterItems = userList.filter { it.name.contains(search,ignoreCase = false) }
            items(filterItems) {
                Spacer(modifier = Modifier.height(10.dp))
                UserItem(it,navController)
            }
        }

    }

}