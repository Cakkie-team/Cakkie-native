package com.cakkie.ui.screens.wallet.earn

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.screens.destinations.BrowserDestination
import com.cakkie.ui.screens.destinations.LeaderBoardDestination
import com.cakkie.ui.screens.destinations.ReferralDestination
import com.cakkie.ui.screens.wallet.WalletViewModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieBrown002
import com.cakkie.ui.theme.CakkieOrange
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.Toaster
import com.cakkie.utill.formatNumber
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Earn(navigator: DestinationsNavigator) {
    val viewModal: WalletViewModel = koinViewModel()
    val user = viewModal.user.observeAsState().value
    val balance = viewModal.balance.observeAsState(listOf()).value
    val context = LocalContext.current as Activity
    val spkBalance = formatNumber(
        balance.find { it.symbol == "SPK" }?.balance ?: 0.0, 3
    ).split(".")
    var couldMine by remember { mutableStateOf(false) }

    var mindedSpk by remember { mutableStateOf("") }
    var remainingTime by remember { mutableLongStateOf(0) }
    var gettingAd by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val uriHandle = LocalUriHandler.current

//    var loadingAd by remember {
//        mutableStateOf(true)
//    }

//    var countDownTimer by remember { mutableIntStateOf(0) }

    // Count down timer
//    LaunchedEffect(key1 = gettingAd, key2 = loadingAd) {
//        if (gettingAd || loadingAd) {
//            countDownTimer = 15
//            while (countDownTimer > 0) {
//                delay(1000)
//                countDownTimer -= 1
//            }
//            gettingAd = false
//            loadingAd = false
//        }
//    }
    LaunchedEffect(key1 = user) {
        couldMine = false

        //current data time in iso
        val currentDateTime =
            LocalDateTime.now().minusHours(1).minusMinutes(15)
                .format(DateTimeFormatter.ISO_DATE_TIME)
        val targetDateTime = LocalDateTime.parse(
            user?.lastMine?.ifEmpty { currentDateTime } ?: currentDateTime,
            DateTimeFormatter.ISO_DATE_TIME
        )

        // Add 2 hours to the target time
        val targetMillis =
            targetDateTime.plusHours(1).plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()
        var spkPerMillis = (user?.earningRate ?: 0.0) / 900000
        var totalSpkMined = spkPerMillis * 900000
        var totalSpkMinedStr = formatNumber(totalSpkMined)
        mindedSpk = "$totalSpkMinedStr SPK"
        while (Instant.now().toEpochMilli() < targetMillis) {
            val currentTime = Instant.now().toEpochMilli()
            val remainingMillis = targetMillis - currentTime

//            Timber.d("Remaining time: $remainingMillis")

//            val hours = remainingMillis / (1000 * 60 * 60)
//            val minutes = (remainingMillis % (1000 * 60 * 60)) / (1000 * 60)
//            val seconds = ((remainingMillis % (1000 * 60 * 60)) % (1000 * 60)) / 1000

            //calculate total spk from remaining time
            spkPerMillis = (user?.earningRate ?: 0.0) / 900000
            totalSpkMined = spkPerMillis * (900000 - remainingMillis)
            totalSpkMinedStr = formatNumber(totalSpkMined)
            mindedSpk = "$totalSpkMinedStr SPK"
            remainingTime = remainingMillis
//            remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

            delay(1000) // Delay for 1 second
        }

        // Countdown finished
        couldMine = true
    }
    /* var rewardedAd by remember {
         mutableStateOf<RewardedAd?>(null)
     }*/
    var interstitialAd by remember {
        mutableStateOf<InterstitialAd?>(null)
    }

    var retryCount by remember {
        mutableIntStateOf(0)
    }

    /*rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            Timber.d("Ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Set the ad reference to null so you don't show the ad a second time.
            Timber.d("Ad dismissed fullscreen content.")
            rewardedAd = null
            viewModal.mine()
                .addOnSuccessListener {
                    viewModal.getProfile()
                    viewModal.getBalance()
                }
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            // Called when ad fails to show.
            Timber.e("Ad failed to show fullscreen content.")
            rewardedAd = null
        }

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            Timber.d("Ad recorded an impression.")
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Timber.d("Ad showed fullscreen content.")
        }
    }*/

    interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            Timber.d("Ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            Timber.d("Ad dismissed fullscreen content.")
            interstitialAd = null
            gettingAd = false
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            // Called when ad fails to show.
            Timber.e("Ad failed to show fullscreen content.")
            interstitialAd = null
        }

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            Timber.d("Ad recorded an impression.")
            gettingAd = false
            viewModal.mine()
                .addOnSuccessListener {
                    viewModal.getProfile()
                    viewModal.getBalance()
                }
            Timber.d("User earned the reward.")
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Timber.d("Ad showed fullscreen content.")
            gettingAd = false
        }
    }
    LaunchedEffect(key1 = retryCount) {
        viewModal.getBalance()

        if (retryCount > 5) {
            gettingAd = false
            Toaster(
                context,
                "Failed to get ad, Rewarded. Enjoy your Sprinkles",
                R.drawable.logo
            ).show()
            viewModal.mine()
                .addOnSuccessListener {
                    viewModal.getProfile()
                    viewModal.getBalance()
                }
            Timber.d("User earned the reward.")
        }

        /*if (rewardedAd == null && retryCount <= 5 && couldMine) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            if (retryCount % 2 == 0) "ca-app-pub-8613748949810587/7282817310" else "ca-app-pub-8613748949810587/1943587076",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.d(adError.toString())
                    rewardedAd = null
                    retryCount += 1
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Timber.d("Ad was loaded.")
                    rewardedAd = ad
                    if (gettingAd) {
                        ad.show(context) { rewardItem ->
                            gettingAd = false
                            // Handle the reward.
//                        val rewardAmount = rewardItem.amount
//                        val rewardType = rewardItem.type
                            viewModal.mine()
                                .addOnSuccessListener {
                                    viewModal.getProfile()
                                    viewModal.getBalance()
                                }
                            Timber.d("User earned the reward.")
                        }
                    }
                }
            })
    }*/

        if (interstitialAd == null && retryCount <= 5) {
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(
                context,
                if (retryCount % 2 == 0) "ca-app-pub-8613748949810587/8957142430" else "ca-app-pub-8613748949810587/9349940392",
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Timber.d(adError.toString())
                        interstitialAd = null
                        retryCount += 1
                    }

                    override fun onAdLoaded(ad: InterstitialAd) {
                        Timber.d("Ad was loaded.")
                        interstitialAd = ad
                        if (gettingAd) {
                            gettingAd = false
                            ad.show(context)
                        }
                    }
                })
        }
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
                            text = "+" + formatNumber(user?.earningRate ?: 0.0),
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieOrange,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "SPK/15mins",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Top)
                        )
                    }
                }
            }
            IconButton(modifier = Modifier
//                .padding(10.dp)
                .align(Alignment.TopEnd),
                onClick = { navigator.navigate(LeaderBoardDestination) }) {
                Icon(
                    painter = painterResource(id = R.drawable.leaderboard),
                    contentDescription = "leaderboard",
                    modifier = Modifier
                        .size(24.dp),
                    tint = CakkieBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(
                    CakkieBrown002.copy(0.4f),
                    RoundedCornerShape(50)
                )
                .fillMaxWidth(0.7f)
                .height(30.dp)
                .clip(RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .background(CakkieBrown002.copy(0.8f), RoundedCornerShape(50))
                    .fillMaxHeight()
                    .fillMaxWidth((900000 - remainingTime).div(900000f))
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "Amount to mine:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Top)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = mindedSpk,
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
                    TaskM(
                        "Invite a friend",
                        "",
                        R.drawable.invite,
                        "Increase your earnings when you invite your friends."
                    ),
                    TaskM(
                        "Repost pinned post",
                        "https://x.com/cakkiefoods",
                        R.drawable.x,
                        "Repost pinned post on X to earn 1000SPK"
                    ),
                    TaskM(
                        "Post on facebook",
                        "https://web.facebook.com/cakkiefoods",
                        R.drawable.facebook,
                        "Spread the news about onboarding backers to earn 1000SPK"
                    ) {
                        //navigate to facebook to post
                        val post =
                            """
                            Attention Bakers and Pastry Chefs!
                            Are you ready to take your passion to the next level?
                            Bringing to you the ultimate app designed just for you with all the amazing perks and benefits!
                            
                            Download the app through this link to get started 
                            https://cakkie.com?referral=${user?.referralCode}
                            
                            #cakkiefoods #cakes #bakeYourWayToSuccess
                        """.trimIndent()
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, post)
                        intent.setPackage("com.facebook.katana")
                        // Check if Instagram is installed before starting the activity
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toaster(
                                context,
                                "Instagram is not installed on your device",
                                R.drawable.logo
                            ).show()
                        }
//                        if (intent.resolveActivity(context.packageManager) != null) {
//                        } else {
//                            Toaster(
//                                context,
//                                "Instagram is not installed on your device",
//                                R.drawable.logo
//                            ).show()
//                        }
                    },
                    TaskM(
                        "Post on X",
                        "https://x.com/cakkiefoods",
                        R.drawable.x,
                        "Spread the news about onboarding backers to earn 1000SPK"
                    ) {
                        //navigate to X to post
                        val post =
                            "Bake your way to success effortlessly by downloading and using the Cakkie app, a platform designed for everything sweet .\n" +
                                    "https://cakkie.com?referral=${user?.referralCode}\n" +
                                    "\n" +
                                    "#cakkiefoods #cakes #bakers #PastryChef #bakeYourWayToSuccess"
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, post)
                        intent.setPackage("com.twitter.android")
                        // Check if x is installed before starting the activity
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toaster(
                                context,
                                "Instagram is not installed on your device",
                                R.drawable.logo
                            ).show()
                        }
//                        if (intent.resolveActivity(context.packageManager) != null) {
//                        } else {
//                            Toaster(
//                                context,
//                                "X is not installed on your device",
//                                R.drawable.logo
//                            ).show()
//                        }
                    },
                    TaskM(
                        "Follow us on X",
                        "https://x.com/cakkiefoods",
                        R.drawable.x,
                        "Follow Cakkiefoods on X to earn 1000SPK"
                    ),
                    TaskM(
                        "Subscribe on youtube",
                        "https://youtube.com/@CakkieFoods",
                        R.drawable.youtube_icon,
                        "Subscribe to our youtube channel and earn 1000SPK"
                    ),
                    TaskM(
                        "Follow us on instagram",
                        "https://www.instagram.com/cakkiefoods/",
                        R.drawable.instagram,
                        "Follow Cakkiefoods on instagram to earn 1000SPK"
                    ),
                    TaskM(
                        "Follow us on Facebook",
                        "https://web.facebook.com/cakkiefoods",
                        R.drawable.facebook,
                        "Follow our page on facebook to earn 1000SPK"
                    ),
                    TaskM(
                        "Join Whatsapp community",
                        "https://chat.whatsapp.com/LnozCm09MLsAsn5pY2VASg",
                        R.drawable.whatsapp_icon,
                        "Join the Whatsapp community and be rewarded 1000SPK"
                    ),
                    TaskM(
                        "Follow us on Linkedin",
                        "https://www.linkedin.com/company/cakkie",
                        R.drawable.linkedin,
                        "Follow Cakkiefoods on Linkedin to earn 1000SPK"
                    ),
                    TaskM(
                        "Join Telegram community",
                        "https://t.me/cakkieIcing",
                        R.drawable.telegram,
                        "Join the Whatsapp community and be rewarded 1000SPK"
                    )
                )
            ) {
                var state by remember {
                    mutableStateOf("Claim")
                }
                if (user != null) {
                    if (user.earningRate > 20.5 && it.title.contains(("Invite"))) state = "Claimed"
                    if (user.rewards.contains(it.title)) state = "Claimed"
                }
                Row(Modifier.clickable {
                    when (it.title) {
                        "Invite a friend" -> navigator.navigate(ReferralDestination)
                        else -> {
                            it.action()
                            if (it.title !in listOf(
                                    "Post on facebook",
                                    "Post on X"
                                )
                            ) uriHandle.openUri(it.url)
                            state = "Verifying"
                            scope.launch {
                                delay(10000)
                                Toaster(
                                    context,
                                    "Be sure to complete to task",
                                    R.drawable.logo
                                ).show()
                                delay(10000)
                                viewModal.mine(it.title).addOnSuccessListener {
                                    viewModal.getProfile()
                                    viewModal.getBalance()
                                    state = "Claimed"
                                }.addOnFailureListener {
                                    Toaster(
                                        context,
                                        it.localizedMessage ?: it.message
                                        ?: "Unable to claim, can't verify",
                                        R.drawable.logo
                                    ).show()
                                }
                            }
                        }
                    }
                }, verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(18.dp)
                            .border(1.dp, CakkieBrown, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (state === "Claimed") {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(CakkieBrown, CircleShape)
                                    .size(9.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        painter = painterResource(id = it.icon),
                        contentDescription = "task Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Text(
                            text = it.title + if (it.title.contains("Invite")) "" else "  -  $state  1000SPK",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CakkieBrown,
                        )
                        Text(
                            text = it.description,
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
                    retryCount = 0
                    /*  if (couldMine) {
                          rewardedAd?.let { ad ->
                              ad.show(context) { rewardItem ->
                                  gettingAd = false
                                  // Handle the reward.
  //                        val rewardAmount = rewardItem.amount
  //                        val rewardType = rewardItem.type
                                  viewModal.mine()
                                      .addOnSuccessListener {
                                          viewModal.getProfile()
                                          viewModal.getBalance()
                                      }
                                  Timber.d("User earned the reward.")
                              }
                          } ?: run {
  //                    gettingAd = false
  //                    Toaster(
  //                        context,
  //                        "Ad wasn't ready yet, wait for 3secs and try again",
  //                        R.drawable.logo
  //                    ).show()
                              Timber.d("The rewarded ad wasn't ready yet.")
                          }
                      } else {*/
                    interstitialAd?.show(context)
//                    }
                }
                gettingAd = true
            },
            enabled = !gettingAd,
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
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Text(
//                        text = "$countDownTimer seconds remaining",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = CakkieBackground,
//                    )
                }
            }
        }
    }

}

data class TaskM(
    val title: String,
    val url: String = "",
    val icon: Int,
    val description: String,
    val action: () -> Unit = {}
)