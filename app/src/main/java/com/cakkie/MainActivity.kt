package com.cakkie

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.cakkie.BottomState.hideNav
import com.cakkie.navigations.BottomNav
import com.cakkie.ui.screens.NavGraphs
import com.cakkie.ui.screens.appCurrentDestinationAsState
import com.cakkie.ui.screens.destinations.AssetDetailsDestination
import com.cakkie.ui.screens.destinations.CakespirationDestination
import com.cakkie.ui.screens.destinations.ChatListDestination
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.ConfirmPinDestination
import com.cakkie.ui.screens.destinations.DepositDestination
import com.cakkie.ui.screens.destinations.EditVideoDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.JobsDestination
import com.cakkie.ui.screens.destinations.OrdersDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.destinations.SplashScreenDestination
import com.cakkie.ui.screens.destinations.SubscriptionDestination
import com.cakkie.ui.screens.destinations.WalletDestination
import com.cakkie.ui.theme.BackgroundImageId
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import timber.log.Timber


//@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
//var sheetState: BottomSheetNavigatorSheetState = BottomSheetNavigatorSheetState(
//    sheetState = ModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        density = Density(1f),
////        animationSpec = SwipeableDefaults.AnimationSpec
//    )
//)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //check for update
        checkForUpdate()
        val appPackageName = packageManager.getPackageInfo(packageName, 0).packageName
        //request notification permission
//        askNotificationPermission()
        askMultiplePermissions()
//        Appodeal.setMrecViewId(R.id.appodealMrecView)
//        Appodeal.initialize(
//            this, "cbd3296cd5288550da77f4ae2078cab384f5a24ab28d7f87",
//            Appodeal.INTERSTITIAL
//        ) {
//            // Appodeal initialization finished
//            it?.forEach { adType ->
//                Timber.d("AdType: $adType")
//            }
//        }

        //handle notification from firebase
        val extra = intent.extras
        if (extra != null) {
            Timber.d("Notification: $extra")
        }
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator(skipHalfExpanded = true)
            val navController = rememberAnimatedNavController(bottomSheetNavigator)
//            sheetState = bottomSheetNavigator.navigatorSheetState
            //current destination
            val currentDestination = navController.appCurrentDestinationAsState().value
            //set background
            window.setBackgroundDrawableResource(BackgroundImageId(isSystemInDarkTheme()))

            LaunchedEffect(key1 = currentDestination) {
                if (currentDestination == ExploreScreenDestination) {
                    askNotificationPermission()
                }
            }
            CakkieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    ModalBottomSheetLayout(
                        bottomSheetNavigator = bottomSheetNavigator,
                        sheetBackgroundColor = CakkieBackground,
                        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = BackgroundImageId(isSystemInDarkTheme())),
                                contentDescription = "background",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                            Box(
                                Modifier.padding(
                                    top = when (currentDestination) {
                                        WalletDestination -> 0.dp
                                        DepositDestination -> 0.dp
                                        EditVideoDestination -> 0.dp
                                        AssetDetailsDestination -> 0.dp
                                        SplashScreenDestination -> 0.dp
                                        CakespirationDestination -> 16.dp
                                        ExploreScreenDestination -> 20.dp
                                        CommentDestination -> 16.dp
                                        SubscriptionDestination -> 10.dp
                                        ConfirmPinDestination -> 10.dp
                                        else -> 26.dp
                                    }
                                )
                            ) {
                                val isKeyboardOpen by keyboardAsState()
                                DestinationsNavHost(
                                    navGraph = NavGraphs.root,
                                    navController = navController,
                                    modifier = Modifier
                                        .padding(
                                            bottom = if (isKeyboardOpen) 0.dp else
                                                when (currentDestination) {
                                                    JobsDestination -> 50.dp
                                                    ShopDestination -> 50.dp
                                                    ChatListDestination -> 50.dp
                                                    OrdersDestination -> 50.dp
                                                    else -> 0.dp
                                                }
                                        )
                                        .fillMaxSize(),
                                    engine = rememberAnimatedNavHostEngine(
//                                        navHostContentAlignment = Alignment.TopCenter,
//                                        rootDefaultAnimations = RootNavGraphDefaultAnimations(
//                                            enterTransition = { slideInHorizontally() },
//                                            exitTransition = { slideOutHorizontally() }
//                                        ),
//                                        defaultAnimationsForNestedNavGraph = mapOf(
//                                            NavGraphs.root to NestedNavGraphDefaultAnimations(
//                                                enterTransition = { slideInHorizontally() },
//                                                exitTransition = { slideOutHorizontally() }
//                                            ),
//                                        )
                                    )
                                )


                                BottomNav(
                                    navController = navController,
                                    state = (!hideNav.value && !isKeyboardOpen) && when (currentDestination) {
                                        ExploreScreenDestination -> true
                                        JobsDestination -> true
                                        ShopDestination -> true
                                        ChatListDestination -> true
                                        OrdersDestination -> true
                                        else -> false
                                    },
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

    // Checks that the update is not stalled during 'onResume()'.
// However, you should execute this check at all entry points into the app.
//    override fun onResume() {
//        super.onResume()
//        checkForUpdate()
//    }

    private fun checkForUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)

// Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        val activityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult()
            ) { result: ActivityResult ->
                // handle callback
                if (result.resultCode != RESULT_OK) {
                    Timber.d("Update flow failed! Result code: " + result.resultCode)
                    // If the update is canceled or fails,
                    // you can request to start the update again.

                }
            }
// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                && appUpdateInfo.updatePriority() >= 4 /* high priority */
                        // This example applies an immediate update. To apply a flexible update
                        // instead, pass in AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
                || appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            ) {
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // an activity result launcher registered via registerForActivityResult
                    activityResultLauncher,
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }
    }

    //request multiple permissions
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun askMultiplePermissions() {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_VIDEO,
        )
        val requestCode = 101
        requestPermissions(permissions, requestCode)
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
//                Log.e(TAG, "PERMISSION_GRANTED")
                // FCM SDK (and your app) can post notifications.
            } else {
//                Log.e(TAG, "NO_PERMISSION")
                // Directly ask for the permission
                requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

        } else {
//            Toast.makeText(
//                this, "${getString(R.string.app_name)} can't post notifications without Notification permission",
//                Toast.LENGTH_LONG
//            ).show()

            Snackbar.make(
                findViewById(android.R.id.content),
                "Cakkie can't post notifications without Notification permission",
                Snackbar.LENGTH_LONG
            ).setAction("Go to Settings") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val settingsIntent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    startActivity(settingsIntent)
                }
            }.show()
        }
    }
}

object BottomState {
    var hideNav = mutableStateOf(false)
}
object ActivityStates {
    var temp = mutableStateOf<Any?>(null)
}

@ExperimentalMaterialNavigationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberBottomSheetNavigator(
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    skipHalfExpanded: Boolean = false,
): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = animationSpec,
        skipHalfExpanded = skipHalfExpanded,
    )
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
//    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
//    return rememberUpdatedState(isImeVisible)
    var isKeyboardOpen by remember {
        mutableStateOf(false)
    }
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            // ... do anything you want here with `isKeyboardOpen`
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return rememberUpdatedState(isKeyboardOpen)
}