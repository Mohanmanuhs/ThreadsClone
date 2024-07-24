package com.example.threadsclone.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadsclone.model.UserModel
import com.example.threadsclone.navigation.NavRoutes


@Composable
fun UserItem(user: UserModel, navController: NavHostController, modifier: Modifier = Modifier) {


    Card(modifier = Modifier
        .fillMaxWidth(.8f)
        .clickable {
            val routes = NavRoutes.OtherUserDetails.route.replace("{data}",user.uid)
            navController.navigate(routes)
        }) {
        Column(modifier = Modifier.padding(0.dp)) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(model = user.toString),
                    contentDescription = "dp",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        ,
                    contentScale = ContentScale.Crop
                )
                Text(text = user.username, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Text(
                modifier = Modifier.padding(start = 50.dp),
                text = user.name,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
