package com.cakkie.ui.screens.wallet.earn


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.wallet.WalletViewModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieBrown002
import com.cakkie.ui.theme.CakkieOrange
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Referral(navigator: DestinationsNavigator) {
    val viewModel: WalletViewModel = koinViewModel()
    val user = viewModel.user.observeAsState().value
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val history = (viewModel.referrals.observeAsState().value
        ?: emptyList()).sortedByDescending { it.earningRate }

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(
            Intent.EXTRA_TEXT,
            "Sweet news! Mining Icing is now live on Cakkie! Join me in this rich adventure and start earning sweet rewards. Use my referral code: ${user?.referralCode}. Let's indulge together! \uD83C\uDF89 Join here: https://cakkie.com?referral=${user?.referralCode}"
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share your referral code")

    val dec = DecimalFormat("#,##0.00")
//    val adView = remember {
//        AdView(context).apply {
//            adUnitId = "ca-app-pub-8613748949810587/1273874365"
//            setAdSize(getAdSize(context))
//            loadAd(AdRequest.Builder().build())
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = { navigator.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "go back", modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.your_referrals),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.your_friends),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (history.isNotEmpty()) {
            LazyColumn(
                Modifier
                    .fillMaxHeight(0.65f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(
                    items = history,
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            GlideImage(
                                imageModel = it.profileImage.replace(
                                    Regex("\\bhttp://"),
                                    "https://"
                                ),
                                contentDescription = "profile image",
                                modifier = Modifier
                                    .background(CakkieBackground, CircleShape)
                                    .size(40.dp)
                                    .padding(end = 5.dp)
                                    .clip(shape = CircleShape),
                                contentScale = ContentScale.Fit,
                                shimmerParams = ShimmerParams(
                                    baseColor = CakkieBrown.copy(0.8f),
                                    highlightColor = CakkieBackground,
                                    durationMillis = 1000,
                                    dropOff = 0.5f,
                                    tilt = 20f
                                ),
                            )
                            Column {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 18.sp
                                )

                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 3.dp)
                                ) {
                                    Text(
                                        text = "+" + dec.format(it.earningRate),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = CakkieBrown,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "SPK/15mins",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = CakkieOrange,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.Top)
                                    )
                                }
                            }

                        }

                        Text(
                            text = "${history.indexOf(it) + 1}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.55f)
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.8f)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "You have not invited any friend yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CakkieBrown,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Earn rewards and increase your earning rate by inviting friends to Cakkie",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CakkieBrown,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Column(
            Modifier.align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                onClick = {
                    clipboardManager.setText(AnnotatedString(user?.referralCode ?: ""))
                    Toaster(
                        context,
                        "Referral code copied to clipboard",
                        R.drawable.logo
                    ).show()
                },
                shape = RoundedCornerShape(50),
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBrown002.copy(alpha = 0.5f),
                )
            ) {
                Text(
                    text = user?.referralCode ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                )
            }
            IconButton(onClick = {
                clipboardManager.setText(AnnotatedString(user?.referralCode ?: ""))
                Toaster(
                    context,
                    "Referral code copied to clipboard",
                    R.drawable.logo
                ).show()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.copy),
                    contentDescription = "go back", modifier = Modifier.size(24.dp)
                )
            }
        }

        CakkieButton(
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f),
            text = stringResource(id = R.string.share)
        ) {
            startActivity(context, shareIntent, null)
        }
//        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
//            AndroidView(factory = { adView }) { view ->
//                // Add any necessary modifications to the ad view here
//            }
//        }
    }
}

//private fun getAdSize(context: Context): AdSize {
//    val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
//    val outMetrics = DisplayMetrics()
//    display.getMetrics(outMetrics)
//
//    val density = outMetrics.density
//
//    val adWidthPixels = outMetrics.widthPixels.toFloat()
//    val adWidth = (adWidthPixels / density).toInt()
//    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
//}