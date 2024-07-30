package com.example.threadsclone.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threadsclone.model.UserModel
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.ui.theme.rowBgColor
import com.example.threadsclone.ui.theme.txtColor
import com.example.threadsclone.ui.theme.txtHintColor


@Composable
fun UserItem(user: UserModel, navController: NavHostController, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val routes = NavRoutes.OtherUserDetails.route.replace("{data}", user.uid)
                navController.navigate(routes)
            },
        shape = RectangleShape,
        colors = CardDefaults.cardColors().copy(containerColor = rowBgColor)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(model = user.toString),
                contentDescription = "dp",
                modifier = Modifier
                    .padding(5.dp)
                    .size(45.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.fillMaxHeight().padding(start = 5.dp), verticalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier.padding(0.dp),
                    text = user.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp, color = txtColor
                )
                Text(
                    modifier = Modifier.padding(0.dp),
                    text = user.username,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp, color = txtHintColor
                )
            }

        }
    }
}
