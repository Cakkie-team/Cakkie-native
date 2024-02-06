package com.cakkie.ui.screens.shop.listings.bottomUi


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.DeleteListingDestination
import com.cakkie.ui.screens.destinations.PreviewListingDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Error
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun DeleteListing(
    item: Listing,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val viewModel: ShopViewModel = koinViewModel()
    var processing by remember {
        mutableStateOf(false)
    }
    var available by remember {
        mutableStateOf(item.available)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Unspecified)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(80.dp)
                .height(5.dp)
                .clip(CircleShape)
                .background(CakkieBrown)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "delete",
                modifier = Modifier
                    .size(24.dp)
                    .padding(horizontal = 5.dp),
                tint = Error
            )
            Text(
                text = stringResource(id = R.string.delete_listing),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        Text(
            text = stringResource(id = R.string.delete_listing_desc),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontSize = 10.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        CakkieButton(
            Modifier
                .fillMaxWidth(),
            processing = processing,
            text = stringResource(id = R.string.sure)
        ) {
//            viewModel.setAvailability(item.id, available).addOnSuccessListener {
//                processing = false
//
//            }
//                .addOnFailureListener {
//                    processing = false
//                    Toaster(context, it.localizedMessage, R.drawable.logo)
//                }
            navigator.navigate(ShopDestination) {
                launchSingleTop = true
                popUpTo(DeleteListingDestination) {
                    inclusive = true
                }
                popUpTo(PreviewListingDestination) {
                    inclusive = true
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

}