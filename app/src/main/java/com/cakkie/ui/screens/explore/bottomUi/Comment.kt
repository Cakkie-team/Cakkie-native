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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.data.db.models.User
import com.cakkie.networkModels.Listing
import com.cakkie.ui.components.CommentInput
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.MyProfileDestination
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun Comment(item: Listing = Listing(), navigator: DestinationsNavigator) {
    val viewModal: ExploreViewModal = koinViewModel()
    val user = viewModal.user.observeAsState(User()).value

    //get screen height
    val screenHeight = LocalConfiguration.current.screenHeightDp
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
            modifier = Modifier
                .heightIn(min = (screenHeight * 0.4).dp, max = (screenHeight * 0.8).dp)
        ) {
            items(50) {
                CommentItem()
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Divider(
                Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = CakkieBrown
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = user.profileImage.ifEmpty { "https://source.unsplash.com/60x60/?profile" },
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
                        }
                )
//            Spacer(modifier = Modifier.width(8.dp))
                CommentInput {
                    viewModal.commentListing(item.id, user.id, it)
                }
            }
        }
    }
}