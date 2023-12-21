package com.cakkie.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton2
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Settings(navigator: DestinationsNavigator) {

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",

                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navigator.popBackStack()
                    }
                    .size(24.dp)
            )

            Text(
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(id = R.drawable.setting_profile),
                contentDescription = "Settings Profile",
                modifier = Modifier
                    .clickable { }
                    .size(100.dp)
            )
            Text(
                text = "Jennifer Victor",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(Modifier.height(9.dp))

            CakkieButton2(
                text = stringResource(id = R.string.edit_profile)
            ) {
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(

            ) {
                Image(
                    painterResource(id = R.drawable.bell),
                    contentDescription = "notification",
                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.notifications),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )

                    Text(
                        text = stringResource(id = R.string.job_post_posting_followers_following),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontSize = 10.sp
                    )

                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            Image(
                painterResource(id = R.drawable.expand),
                contentDescription = "expand",
                modifier = Modifier.size(height = 24.dp, width = 24.dp)

            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(

            ) {
                Image(
                    painterResource(id = R.drawable.shield),
                    contentDescription = "notification",
                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.notifications),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )

                    Text(
                        text = stringResource(id = R.string.job_post_posting_followers_following),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontSize = 10.sp
                    )

                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            Image(
                painterResource(id = R.drawable.expand),
                contentDescription = "expand",
                modifier = Modifier.size(height = 24.dp, width = 24.dp)

            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(

            ) {
                Image(
                    painterResource(id = R.drawable.bell),
                    contentDescription = "notification",
                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.notifications),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )

                    Text(
                        text = stringResource(id = R.string.job_post_posting_followers_following),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontSize = 10.sp
                    )

                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            Image(
                painterResource(id = R.drawable.expand),
                contentDescription = "expand",
                modifier = Modifier.size(height = 24.dp, width = 24.dp)

            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(

            ) {
                Image(
                    painterResource(id = R.drawable.info),
                    contentDescription = "notification",
                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.notifications),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )

                    Text(
                        text = stringResource(id = R.string.job_post_posting_followers_following),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontSize = 10.sp
                    )

                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            Image(
                painterResource(id = R.drawable.expand),
                contentDescription = "expand",
                modifier = Modifier.size(height = 24.dp, width = 24.dp)

            )
        }

    }

}

