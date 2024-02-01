package com.cakkie.ui.screens.shop.listings.bottomUi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.networkModels.Listing
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun SetAvailability(
    item: Listing,
    onComplete: ResultBackNavigator<Listing>,
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

//            Image(
//                painter = painterResource(id = R.drawable.edit),
//                contentDescription = "approved",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .size(20.dp)
//                    .padding(horizontal = 5.dp)
//
//
//            )
            Text(
                text = stringResource(id = R.string.set_availability),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        Text(
            text = stringResource(id = R.string.set_availability_desc),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontSize = 10.sp
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            listOf(
                R.string.availabile,
                R.string.unavailable
            ).forEach {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        available = it == R.string.availabile
                    }
                ) {
                    RadioButton(
                        selected = if (it == R.string.availabile) available else !available,
                        onClick = {
                            available = it == R.string.availabile
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = CakkieBrown,
                            unselectedColor = CakkieBrown,
                        )

                    )

                    Text(text = stringResource(id = it))

                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        CakkieButton(
            Modifier
                .fillMaxWidth(),
            processing = processing,
            text = stringResource(id = R.string.done)
        ) {
            viewModel.setAvailability(item.id, available).addOnSuccessListener {
                processing = false
                onComplete.navigateBack(result = it)
            }
                .addOnFailureListener {
                    processing = false
                    Toaster(context, it.localizedMessage, R.drawable.logo)
                }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

}