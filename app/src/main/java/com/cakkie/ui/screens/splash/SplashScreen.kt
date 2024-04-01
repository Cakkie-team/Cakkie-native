package com.cakkie.ui.screens.splash

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.cakkie.R
import com.cakkie.ui.screens.NavGraphs
import com.cakkie.ui.screens.destinations.EmailScreenDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.SplashScreenDestination
import com.cakkie.utill.Constants
import com.cakkie.utill.VideoPreLoadingService
import com.cakkie.utill.isVideoUrl
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@RootNavGraph(start = true)
@Composable
fun SplashScreen(navigator: DestinationsNavigator) {
    val viewModel: SplashViewModel = koinViewModel()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value
    val isReady = viewModel.isReady.collectAsState().value
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    val listings = viewModel.retrieveListings().observeAsState().value
    //navigate to the next screen after 2 seconds
    LaunchedEffect(key1 = isReady) {
        navigator.popBackStack(NavGraphs.root.startRoute, inclusive = false)
        if (isReady) {
            if (isLoggedIn) {
                viewModel.getListings()
                    .addOnSuccessListener {
                        viewModel.updateListings(it)
                        val videoList = it.data.filter {
                            it.media.any { it.isVideoUrl() }
                        }.map { it.media.filter { it.isVideoUrl() }.joinToString(",") }
                            .joinToString(",")
//                        Timber.d("Video list: ${videoList.split(",")}")
                        val array = arrayListOf<String>()
                        array.addAll(videoList.split(","))

                        //create intent
                        val preloadingServiceIntent =
                            Intent(context, VideoPreLoadingService::class.java)
                        preloadingServiceIntent.putStringArrayListExtra(Constants.VIDEO_LIST, array)

                        //start intent
                        context.startService(preloadingServiceIntent)
                        //navigate to home
                        navigator.navigate(ExploreScreenDestination) {
                            popUpTo(SplashScreenDestination.route) {
                                inclusive = true
                            }
                        }
                    }
                    .addOnFailureListener {
                        if (listings != null) {
                            val videoList = listings.filter {
                                it.media.any { it.isVideoUrl() }
                            }.map { it.media.filter { it.isVideoUrl() }.joinToString(",") }
                                .joinToString(",")
//                        Timber.d("Video list: ${videoList.split(",")}")
                            val array = arrayListOf<String>()
                            array.addAll(videoList.split(","))

                            //create intent
                            val preloadingServiceIntent =
                                Intent(context, VideoPreLoadingService::class.java)
                            preloadingServiceIntent.putStringArrayListExtra(
                                Constants.VIDEO_LIST,
                                array
                            )

                            //start intent
                            context.startService(preloadingServiceIntent)
                            //navigate to home
                            navigator.navigate(ExploreScreenDestination) {
                                popUpTo(SplashScreenDestination.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
            } else {
                navigator.navigate(EmailScreenDestination) {
                    popUpTo(SplashScreenDestination.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = R.drawable.splash_ann).apply(block = {
                    size(Size.ORIGINAL)
                }).build(), imageLoader = imageLoader
            ),
            contentDescription = "Splash animation",
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.Center),
            contentScale = ContentScale.FillWidth
        )
    }
}