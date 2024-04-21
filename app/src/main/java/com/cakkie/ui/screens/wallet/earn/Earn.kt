package com.cakkie.ui.screens.wallet.earn

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appodeal.ads.Appodeal
import com.appodeal.ads.InterstitialCallbacks
import com.appodeal.ads.RewardedVideoCallbacks
import com.cakkie.R
import com.cakkie.ui.screens.destinations.BrowserDestination
import com.cakkie.ui.screens.destinations.ReferralDestination
import com.cakkie.ui.screens.wallet.WalletViewModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieBrown002
import com.cakkie.ui.theme.CakkieGreen
import com.cakkie.ui.theme.CakkieOrange
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Earn(navigator: DestinationsNavigator) {
    val viewModal: WalletViewModel = koinViewModel()
    val dec = DecimalFormat("#,##0.00")
    val user = viewModal.user.observeAsState().value
    val balance = viewModal.balance.observeAsState(listOf()).value
    val context = LocalContext.current as Activity
    val spkBalance = dec.format(
        balance.find { it.symbol == "SPK" }?.balance ?: 0.0
    ).split(".")
    var couldMine by remember { mutableStateOf(false) }

    var remainingTime by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = user) {
        couldMine = false

        //current data time in iso
        val currentDateTime =
            LocalDateTime.now().minusHours(2).format(DateTimeFormatter.ISO_DATE_TIME)
        val targetDateTime = LocalDateTime.parse(
            user?.lastMine?.ifEmpty { currentDateTime } ?: currentDateTime,
            DateTimeFormatter.ISO_DATE_TIME
        )

        // Add 2 hours to the target time
        val targetMillis =
            targetDateTime.plusHours(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        while (Instant.now().toEpochMilli() < targetMillis) {
            val currentTime = Instant.now().toEpochMilli()
            val remainingMillis = targetMillis - currentTime

            val hours = remainingMillis / (1000 * 60 * 60)
            val minutes = (remainingMillis % (1000 * 60 * 60)) / (1000 * 60)
            val seconds = ((remainingMillis % (1000 * 60 * 60)) % (1000 * 60)) / 1000

            remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

            delay(1000) // Delay for 1 second
        }

        // Countdown finished
        couldMine = true
    }
//    var rewardedAd by remember {
//        mutableStateOf<RewardedAd?>(null)
//    }
    var gettingAd by remember {
        mutableStateOf(false)
    }

    var retryCount by remember {
        mutableIntStateOf(0)
    }

    Appodeal.setRewardedVideoCallbacks(object : RewardedVideoCallbacks {
        override fun onRewardedVideoLoaded(isPrecache: Boolean) {
            // Called when rewarded video is loaded
            Timber.d("Ad was loaded.")
            if (gettingAd) {
                gettingAd = false
                Appodeal.show(context, Appodeal.REWARDED_VIDEO, "Icingmining")
            }
        }

        override fun onRewardedVideoFailedToLoad() {
            // Called when rewarded video failed to load
            gettingAd = false
            Toaster(
                context,
                "Ad failed to load, try again",
                R.drawable.logo
            ).show()
        }

        override fun onRewardedVideoShown() {
            // Called when rewarded video is shown
        }

        override fun onRewardedVideoShowFailed() {
            // Called when rewarded video show failed
            gettingAd = false
            Toaster(
                context,
                "Ad display failed, try again",
                R.drawable.logo
            ).show()
        }

        override fun onRewardedVideoClicked() {
            // Called when rewarded video is clicked
        }

        override fun onRewardedVideoFinished(amount: Double, currency: String) {
            // Called when rewarded video is viewed until the end
            gettingAd = false
            viewModal.mine()
                .addOnSuccessListener {
                    viewModal.getProfile()
                    viewModal.getBalance()
                }
        }

        override fun onRewardedVideoClosed(finished: Boolean) {
            // Called when rewarded video is closed
        }

        override fun onRewardedVideoExpired() {
            // Called when rewarded video is expired
            gettingAd = false
            Toaster(
                context,
                "Ad expired, try again",
                R.drawable.logo
            ).show()
        }
    })

    Appodeal.setInterstitialCallbacks(object : InterstitialCallbacks {
        override fun onInterstitialLoaded(isPrecache: Boolean) {
            // Called when interstitial is loaded
            if (gettingAd) {
                gettingAd = false
                Appodeal.show(context, Appodeal.INTERSTITIAL)
            }
        }

        override fun onInterstitialFailedToLoad() {
            // Called when interstitial failed to load
            Timber.d("Ad failed to load.")
        }

        override fun onInterstitialShown() {
            // Called when interstitial is shown
            Timber.d("interstitial was shown.")
        }

        override fun onInterstitialShowFailed() {
            // Called when interstitial show failed
        }

        override fun onInterstitialClicked() {
            // Called when interstitial is clicked
            Timber.d("Ad was clicked.")
        }

        override fun onInterstitialClosed() {
            // Called when interstitial is closed
            viewModal.mine()
                .addOnSuccessListener {
                    viewModal.getProfile()
                    viewModal.getBalance()
                }
        }

        override fun onInterstitialExpired() {
            // Called when interstitial is expired
        }
    })


//    rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
//        override fun onAdClicked() {
//            // Called when a click is recorded for an ad.
//            Timber.d("Ad was clicked.")
//        }
//
//        override fun onAdDismissedFullScreenContent() {
//            // Called when ad is dismissed.
//            // Set the ad reference to null so you don't show the ad a second time.
//            Timber.d("Ad dismissed fullscreen content.")
//            rewardedAd = null
//        }
//
//        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//            // Called when ad fails to show.
//            Timber.e("Ad failed to show fullscreen content.")
//            rewardedAd = null
//        }
//
//        override fun onAdImpression() {
//            // Called when an impression is recorded for an ad.
//            Timber.d("Ad recorded an impression.")
//        }
//
//        override fun onAdShowedFullScreenContent() {
//            // Called when ad is shown.
//            Timber.d("Ad showed fullscreen content.")
//        }
//    }
    LaunchedEffect(key1 = Unit) {
        viewModal.getBalance()

//        if (retryCount > 5) {
//            gettingAd = false
//            Toaster(
//                context,
//                "Failed to get ad, exit screen and try again",
//                R.drawable.logo
//            ).show()
//        }

//        if (rewardedAd == null && retryCount <= 5) {
//            val adRequest = AdRequest.Builder().build()
//            RewardedAd.load(
//                context,
//                if (retryCount % 2 == 0) "ca-app-pub-8613748949810587/7282817310" else "ca-app-pub-8613748949810587/1943587076",
//                adRequest,
//                object : RewardedAdLoadCallback() {
//                    override fun onAdFailedToLoad(adError: LoadAdError) {
//                        Timber.d(adError.toString())
//                        rewardedAd = null
//                        retryCount += 1
//                    }
//
//                    override fun onAdLoaded(ad: RewardedAd) {
//                        Timber.d("Ad was loaded.")
//                        rewardedAd = ad
//                        if (gettingAd) {
//                            ad.show(context) { rewardItem ->
//                                gettingAd = false
//                                // Handle the reward.
////                        val rewardAmount = rewardItem.amount
////                        val rewardType = rewardItem.type
//                                viewModal.mine()
//                                    .addOnSuccessListener {
//                                        viewModal.getProfile()
//                                        viewModal.getBalance()
//                                    }
//                                Timber.d("User earned the reward.")
//                            }
//                        }
//                    }
//                })
//        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            IconButton(modifier = Modifier
                .align(Alignment.CenterStart), onClick = { navigator.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Text(
                text = "Earning Dashboard",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                color = CakkieBrown,
                fontSize = 18.sp
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .background(CakkieBrown)
                .padding(16.dp)
                .fillMaxWidth(0.9f),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Current Balance",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .background(CakkieBrown002, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = spkBalance[0] + ".",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = spkBalance[1],
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Top)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "SPK",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Earning Rate",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(3.dp))

                Box(
                    modifier = Modifier
                        .background(CakkieBrown002, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "+" + dec.format(user?.earningRate ?: 0.0),
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieOrange,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "SPK/hr",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Top)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(
                    (if (couldMine) CakkieGreen else CakkieBrown002).copy(0.8f),
                    RoundedCornerShape(50)
                )
                .clip(RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "Next session starts",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Top)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = remainingTime,
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieOrange,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            Modifier
                .fillMaxWidth(0.9f)
                .weight(1f)
        ) {
            item {
                Text(
                    text = "Our Journey",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextColorDark,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    Modifier
                        .clickable {
                            navigator.navigate(BrowserDestination("https://cakkie.com/whitepaper"))
                        }
                        .clip(RoundedCornerShape(10))
                        .background(Color.White, RoundedCornerShape(8))
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.whitepaper),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "White Paper",
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextColorDark,
                            )
                            Text(
                                text = "Learn more about our vision at Cakkie",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextColorInactive,
                            )
                        }
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_fill),
                        contentDescription = "arrow",
                        modifier = Modifier.rotate(180f),
                        tint = TextColorDark
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Bonus tasks",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Complete the following tasks below to claim some bonus",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(
                items = listOf(
                    "Invite a friend",
                )
            ) {
                Row(Modifier.clickable {
                    when (it) {
                        "Invite a friend" -> navigator.navigate(ReferralDestination)
                        else -> {}
                    }
                }, verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(18.dp)
                            .border(1.dp, CakkieBrown, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(CakkieBrown, CircleShape)
                                .size(9.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        painter = painterResource(id = R.drawable.invite),
                        contentDescription = "task Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = CakkieBrown,
                        )
                        Text(
                            text = "Increase your earnings when you invite your friends.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextColorDark,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Card(
            onClick = {
                if (!gettingAd) {
                    if (Appodeal.canShow(Appodeal.REWARDED_VIDEO, "Icingmining")) {
                        Appodeal.show(context, Appodeal.REWARDED_VIDEO, "Icingmining")
                    } else {
                        scope.launch {
                            delay(5000)
                            if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
                                gettingAd = false
                                Appodeal.show(context, Appodeal.INTERSTITIAL)
                            }
                        }
                    }
//                    retryCount = 0
//                    rewardedAd?.let { ad ->
//                        ad.show(context) { rewardItem ->
//                            gettingAd = false
//                            // Handle the reward.
////                        val rewardAmount = rewardItem.amount
////                        val rewardType = rewardItem.type
//                            viewModal.mine()
//                                .addOnSuccessListener {
//                                    viewModal.getProfile()
//                                    viewModal.getBalance()
//                                }
//                            Timber.d("User earned the reward.")
//                        }
//                    } ?: run {
////                    gettingAd = false
////                    Toaster(
////                        context,
////                        "Ad wasn't ready yet, wait for 3secs and try again",
////                        R.drawable.logo
////                    ).show()
//                        Timber.d("The rewarded ad wasn't ready yet.")
//                    }
                }
                gettingAd = true
            },
            enabled = !gettingAd && couldMine,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(0.7f),
            colors = CardDefaults.cardColors(
                containerColor = CakkieBrown002,
                disabledContainerColor = CakkieBrown002.copy(alpha = 0.5f),
            )
        ) {
            Row(
                Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Mine",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.bake),
                    contentDescription = "Mine",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }

    //please wait popup
    if (gettingAd) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(20)),
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBrown002
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Please wait",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBackground,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "We are getting your ad ready",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBackground,
                    )
                }
            }
        }
    }

}