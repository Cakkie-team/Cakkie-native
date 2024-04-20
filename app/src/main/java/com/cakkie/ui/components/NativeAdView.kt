//package com.cakkie.ui.components
//
//import android.view.View
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.viewinterop.AndroidViewBinding
//import com.cakkie.databinding.NativeAdBinding
//import com.google.android.gms.ads.AdListener
//import com.google.android.gms.ads.AdLoader
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.nativead.NativeAd
//import com.google.android.gms.ads.nativead.NativeAdOptions
//import com.google.android.gms.ads.nativead.NativeAdOptions.ADCHOICES_TOP_LEFT
//import kotlin.concurrent.fixedRateTimer
//
//@Composable
//fun NativeAdView(id: String) {
//    val context = LocalContext.current
//    val adUnit = "ca-app-pub-8613748949810587/6606609015"
//    var isAdRequested by remember {
//        mutableStateOf(false)
//    }
//    var advert by remember {
//        mutableStateOf<NativeAd?>(null)
//    }
//    AndroidViewBinding(
//        factory = NativeAdBinding::inflate,
//        modifier = Modifier
//            .navigationBarsPadding()
//            .wrapContentHeight(unbounded = true)
//    ) {
//        if (isAdRequested)
//            return@AndroidViewBinding
//        val adView = nativeAdView.also { adView ->
//            adView.adChoicesView = adChoice
//            adView.bodyView = tvBody
//            adView.callToActionView = btnCta
//            adView.headlineView = tvHeadline
//            adView.iconView = ivAppIcon
//            adView.mediaView = mvContent
//        }
//        kotlin.runCatching {
//            AdLoader.Builder(adView.context, adUnit)
//                .forNativeAd { nativeAd ->
//                    advert = nativeAd
//                    nativeAd.body?.let { body ->
//                        tvBody.text = body
//                    }
//
//                    nativeAd.callToAction?.let { cta ->
//                        btnCta.text = cta
//                    }
//
//                    nativeAd.headline?.let { headline ->
//                        tvHeadline.text = headline
//                    }
//
//                    nativeAd.icon?.let { icon ->
//                        ivAppIcon.setImageDrawable(icon.drawable)
//                    }
//
//                    adView.setNativeAd(nativeAd)
//                }
//                .withAdListener(object : AdListener() {
//                    override fun onAdLoaded() {
//                        super.onAdLoaded()
//
//                        shimmerFrameLayout.visibility = View.GONE
//                        adView.visibility = View.VISIBLE
//                    }
//
//                })
//                .withNativeAdOptions(
//                    NativeAdOptions.Builder().setAdChoicesPlacement(ADCHOICES_TOP_LEFT).build()
//                )
//                .build()
//        }.onSuccess {
//            it.loadAd(AdRequest.Builder().build())
//            isAdRequested = true
//            // Reload ad every 1 hour
//            fixedRateTimer(name = id, initialDelay = 3600000, period = 3600000) {
//                isAdRequested = false
//                advert?.destroy()
//                it.loadAd(AdRequest.Builder().build())
//                isAdRequested = true
//            }
//        }
//    }
//}