package com.example.threadsclone.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import coil.compose.rememberAsyncImagePainter
import com.example.threadsclone.model.ThreadModel
import com.example.threadsclone.model.UserModel


@Composable
fun ThreadItem(thread: ThreadModel, user: UserModel, modifier: Modifier = Modifier) {


    Card(modifier = Modifier.fillMaxWidth(.8f)) {
        Column(modifier = Modifier.padding(0.dp)) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(model = user.toString),
                    contentDescription = "dp",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {

                        },
                    contentScale = ContentScale.Crop
                )
                Text(text = user.username, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Text(
                modifier = Modifier.padding(start = 50.dp),
                text = thread.thread,
                fontWeight = FontWeight.Bold
            )

            if(thread.imageUrl.isNotEmpty()) {
                Card {
                    Image(
                        painter = rememberAsyncImagePainter(model = thread.imageUrl),
                        contentDescription = "posted image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clickable {

                            },
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}