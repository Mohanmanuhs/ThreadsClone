package com.example.threadsclone.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.threadsclone.R
import com.example.threadsclone.model.ThreadModel
import com.example.threadsclone.model.UserModel
import com.example.threadsclone.ui.theme.rowBgColor
import com.example.threadsclone.ui.theme.txtColor
import com.example.threadsclone.ui.theme.txtHintColor


@Composable
fun ThreadItem(onClick:(String)->Unit,thread: ThreadModel, user: UserModel,isFollow:Boolean,modifier: Modifier = Modifier,isFollowing:Boolean=false) {

    Card(
        modifier = Modifier
            .fillMaxWidth(), shape = RectangleShape, colors = CardDefaults.cardColors().copy(containerColor = rowBgColor)
    ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = if (user.toString == "") painterResource(id = R.drawable.baseline_account_circle_24) else rememberAsyncImagePainter(
                            model = user.toString
                        ),
                        contentDescription = "dp",
                        modifier = Modifier
                            .padding(5.dp)
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable {

                            },
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier
                        .padding(start = 5.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                        Text(color = txtColor, text = user.name, fontWeight = FontWeight.Medium, fontSize = 20.sp)
                        Text(
                            text = user.bio,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = txtHintColor
                        )
                    }
                    if(isFollow) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            TextButton(modifier = Modifier.align(Alignment.CenterEnd),
                                onClick = { onClick(user.uid) }) {
                                Text(
                                    text = if(isFollowing) "Following" else "Follow",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color(0xFF3A96F5)
                                )
                            }
                        }
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(.95f),
                    text = thread.thread,color = txtColor,
                )

                if (thread.imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = thread.imageUrl
                        ),
                        contentDescription = "posted image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clickable {

                            },
                        contentScale = ContentScale.FillBounds
                    )
                }


        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}