package com.cakkie.ui.screens.explore.bottomUi

import ReportOptions
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.Inactive
import com.cakkie.utill.ContentType
import com.cakkie.utill.Toaster
import com.cakkie.utill.share
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun MoreOptions(
    contentType: ContentType,
    contentId: String,
) {
    val viewModel: ExploreViewModal = koinViewModel()
    val context = LocalContext.current
    var showReportDialog by remember { mutableStateOf(false) }
    var processing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
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

        // loading indicator if processing
        if (processing) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = CakkieBrown
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Share Button
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    context.share(
                        contentType = contentType,
                        contentId = contentId
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.share),
                contentDescription = "share",
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.share),
                style = MaterialTheme.typography.labelLarge,
                color = if (processing) Inactive else CakkieBrown,
                fontSize = 16.sp,
            )
        }
        Divider(
            Modifier
                .clip(RoundedCornerShape(50))
//                .height(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            color = CakkieLightBrown,
            thickness = 1.dp,
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable(enabled = !processing) {
                    processing = true
                    viewModel
                        .flagListing(contentId)
                        .addOnSuccessListener {
                            processing = false
                            Timber
                                .tag("MoreOptions")
                                .d(
                                    "Successfully flagged content: $contentId"
                                )
                            Toaster(
                                context,
                                "Content Flagged",
                                R.drawable.logo
                            ).show()
                        }
                        .addOnFailureListener { error ->
                            processing = false
                            Timber
                                .tag("MoreOptions")
                                .e(error, "Failed to flag content: $error")
                            Toaster(
                                context,
                                "Failed to flag content: $error",
                                R.drawable.logo
                            ).show()
                        }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.flag),
                contentDescription = "flag",
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.flag),
                style = MaterialTheme.typography.labelLarge,
                color = if (processing) Inactive else CakkieBrown,
                fontSize = 16.sp,
            )
        }
        Divider(
            Modifier
                .clip(RoundedCornerShape(50))
//                .height(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            color = CakkieLightBrown,
            thickness = 1.dp,
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable(enabled = !processing) {
                    showReportDialog = true
                    //navigator.navigate(ReportDestination(name = contentName ?: ""))
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.report),
                contentDescription = "report",
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.report),
                style = MaterialTheme.typography.labelLarge,
                color = CakkieBrown,
                fontSize = 16.sp,
            )
        }
        Divider(
            Modifier
                .clip(RoundedCornerShape(50))
//                .height(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            color = CakkieLightBrown,
            thickness = 1.dp,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showReportDialog) {
        ReportOptions(
            onDismiss = { showReportDialog = false },
            onCategorySelected = { category ->
                processing = true
                viewModel.reportListing(contentId, comment = category).addOnSuccessListener {
                    processing = false
                    Timber.tag("MoreOptions")
                        .d("Successfully report content: $contentId for category: $category")
                    Toaster(
                        context,
                        "Reported for: $category",
                        R.drawable.logo
                    ).show()
                }.addOnFailureListener { error ->
                    processing = false
                    Timber.tag("MoreOptions").e("Failed to report content: $error")
                    Toaster(
                        context,
                        "Failed to report content: $error",
                        R.drawable.logo
                    ).show()
                }
            })
    }
}