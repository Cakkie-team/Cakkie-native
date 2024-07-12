package com.cakkie.ui.screens.jobs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.data.db.models.User
import com.cakkie.networkModels.JobEdit
import com.cakkie.networkModels.JobModel
import com.cakkie.networkModels.JobResponse
import com.cakkie.networkModels.Meta
import com.cakkie.ui.components.PageTabs
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.destinations.JobDetailsDestination
import com.cakkie.ui.screens.destinations.SetDeliveryAddressDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun Jobs(
    fileRecipient: ResultRecipient<ChooseMediaDestination, String>,
    addressRecipient: ResultRecipient<SetDeliveryAddressDestination, User>,
    onEdit: ResultRecipient<JobDetailsDestination, JobEdit>,
    navigator: DestinationsNavigator
) {
    val viewModel: JobsViewModel = koinViewModel()
    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp
    val pageState = rememberPagerState(pageCount = { 4 })
    val media = remember {
        mutableStateListOf<MediaModel>()
    }
    val jobRes = viewModel.jobRes.observeAsState(JobResponse()).value
    val jobs = remember {
        mutableStateListOf<JobModel>()
    }

    val myJobRes = viewModel.myJobRes.observeAsState(JobResponse()).value
    val myJobs = remember {
        mutableStateListOf<JobModel>()
    }
    val user = viewModel.user.observeAsState().value
    addressRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.getProfile()
            }
        }
    }
    val job = remember {
        mutableStateOf(
            JobModel(
                currencySymbol = "NGN",
                description = "",
                meta = Meta(
                    flavour = "Vanilla",
                    quantity = "",
                    shape = "Round",
                    size = "4 inches H, 20cm W"
                ),
                productType = "Cake",
            )
        )
    }

    onEdit.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                job.value = result.value.job
                media.addAll(result.value.media)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getJobs()
        viewModel.myJobs()
    }
    LaunchedEffect(user) {
        if (user != null) {
            job.value = job.value.copy(
                state = user.state,
                address = user.address,
                city = user.city,
                country = user.country,
                latitude = user.latitude,
                longitude = user.longitude,
                userId = user.id,
                user = user,
            )
        }
    }
    Column(Modifier) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(
                    id = R.string.cakkie_logo
                ),
                modifier = Modifier
                    .size(27.dp),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(id = R.string.jobs),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = CakkieBrown,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(Modifier.height(height.minus(88.dp))) {
            //page tabs
            PageTabs(
                pagerState = pageState,
                pageCount = pageState.pageCount,
                tabs = listOf(
                    stringResource(id = R.string.all_jobs),
                    stringResource(id = R.string.post_a_job),
                    stringResource(id = R.string.my_jobs),
                )
            )

            HorizontalPager(state = pageState) {
                when (it) {
                    0 -> MyJobs(myJobRes, myJobs, navigator) {
                        viewModel.myJobs(myJobRes.meta.nextPage, myJobRes.meta.pageSize)
                    }

                    1 -> CreateJob(job, viewModel, media, fileRecipient, navigator = navigator)
                    2 -> AllJobs(jobRes, jobs, navigator) {
                        viewModel.getJobs(jobRes.meta.nextPage, jobRes.meta.pageSize)
                    }
                }
            }
        }
    }
}