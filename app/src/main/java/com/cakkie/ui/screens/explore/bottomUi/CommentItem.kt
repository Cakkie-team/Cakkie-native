package com.cakkie.ui.screens.explore.bottomUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.networkModels.Comment
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.utill.formatDate
import com.cakkie.utill.toObject

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CommentItem(item: Comment, userId: String, viewModal: ExploreViewModal) {
    var comment by remember {
        mutableStateOf(item)
    }
    var isLiked by remember {
        mutableStateOf(comment.isLiked)
    }
    DisposableEffect(Unit) {
        viewModal.socket.on("like-${item.id}") {
            val newComment = it[0].toString().toObject(Comment::class.java)
            comment = newComment
        }
        onDispose {
            viewModal.socket.off("like-${item.id}")
        }
    }

    Column(
        Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    model = comment.user.profileImage.replace("http", "https"),
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .clickable {

                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = comment.user.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = comment.createdAt.formatDate(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(
                        text = comment.content,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(
                        id =
                        if (isLiked) R.drawable.gridicons_heart else R.drawable.heart
                    ),
                    contentDescription = "like",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            viewModal.likeComment(item.id, userId)
                            isLiked = !isLiked
                        }
                )
                Text(
                    text = comment.totalLikes.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}