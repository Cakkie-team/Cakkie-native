package com.cakkie.ui.screens.explore.bottomUi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.data.db.models.User
import com.cakkie.networkModels.Comment
import com.cakkie.networkModels.Listing
import com.cakkie.ui.components.CommentInput
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.MyProfileDestination
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.toObject
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun Comment(item: Listing = Listing(), navigator: DestinationsNavigator) {
    val viewModal: ExploreViewModal = koinViewModel()
    val user = viewModal.user.observeAsState(User()).value
    val comments = remember {
        mutableStateListOf<Comment>()
    }
    var loading by remember {
        mutableStateOf(false)
    }
    //get screen height
    val screenHeight = LocalConfiguration.current.screenHeightDp
    LaunchedEffect(item) {
        comments.addAll(item.comments)
        loading = true
        viewModal.getComments(item.id).addOnSuccessListener {
            loading = false
            //update comments list with new comments without duplicating
            comments.addAll(it.data.filter { comment ->
                !comments.map { it.id }.contains(comment.id)
            })
        }
    }
    DisposableEffect(Unit) {
        viewModal.socket.on("comment-${item.id}") {
            Timber.d("comment ${it[0]}")
            val newComment = it[0].toString().toObject(Comment::class.java)
            //update comments list with new comments without duplicating
            if (!comments.map { it.id }.contains(newComment.id)) comments.add(newComment)
        }
        onDispose {
            viewModal.socket.off("comment-${item.id}")
        }
    }
    Column(
        modifier = Modifier
//            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            Modifier
                .clip(RoundedCornerShape(50))
//                .height(8.dp)
                .width(72.dp)
                .align(Alignment.CenterHorizontally),
            color = CakkieBrown,
            thickness = 8.dp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.heightIn(
                min = (screenHeight * 0.4).dp,
                max = (screenHeight * 0.8).dp
            )
        ) {
            items(items = comments, key = { it.id }) {
                CommentItem(it)
            }
            if (comments.isEmpty()) item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.no_comments),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        color = CakkieBrown,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = stringResource(id = R.string.be_the_first_to_comment),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            //add a loading indicator
            if (loading) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp),
                            color = CakkieBrown,
                            strokeWidth = 2.dp,
                            trackColor = CakkieBrown.copy(alpha = 0.4f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }
        Column(
            Modifier.fillMaxWidth()
        ) {
            Divider(
                Modifier.fillMaxWidth(), thickness = 1.dp, color = CakkieBrown
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                GlideImage(model = user.profileImage.replace("http", "https"),
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navigator.navigate(MyProfileDestination) {
                                popUpTo(CommentDestination) {
                                    inclusive = true
                                }
                            }
                        })
//            Spacer(modifier = Modifier.width(8.dp))
                CommentInput {
                    viewModal.commentListing(item.id, user.id, it)
                }
            }
        }
    }
}