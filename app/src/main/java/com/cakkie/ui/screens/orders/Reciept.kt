package com.cakkie.ui.screens.orders

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Reciept () {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .border(width = 2.dp, color = CakkieBrown)
            .height(200.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 37.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cakkie_icon),
                    contentDescription = "icon",
                )
                Text(
                    text = stringResource(id = R.string.reciept),
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                    modifier = Modifier.align(Alignment.Center)

                )
            }
            Spacer(modifier = Modifier.height(20.dp))
               DashDivider(
                 thickness = 1.dp,
                  color = CakkieBrown,
                   modifier =  Modifier
                       .fillMaxWidth()
                   )
            Spacer(modifier = Modifier.height(20.dp))
            Row ( modifier = Modifier
                .fillMaxWidth(),
            ){
Text(
    text = stringResource(id = R.string.transaction_id),
    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "987654321",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                    modifier = Modifier
                .fillMaxWidth()
            ){
Text(
    text = stringResource(id = R.string.transaction_date),
    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Wed, 19th June 2023",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row ( modifier = Modifier
                .fillMaxWidth()
            ){
Text(
    text = stringResource(id = R.string.transatcion_time),
    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "1 : 07PM",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row ( modifier = Modifier
                .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.name_of_reciepient),
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Donald John",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row ( modifier = Modifier
                .fillMaxWidth()
            ){
Text(
    text = stringResource(id = R.string.amount),
    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "NGN 20,000",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row ( modifier = Modifier
                .fillMaxWidth()
            ){
Text(
    text = stringResource(id = R.string.item_purchased),
    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Icing",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            Row ( modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown,
                    fontWeight = FontWeight.Bold,
                    modifier =  Modifier.clickable {}
                )
                Spacer(modifier = Modifier.width(180.dp))
       Text(
           text = stringResource(id = R.string.download),
           style = MaterialTheme.typography.bodyLarge,
           fontWeight = FontWeight.Bold,
           color = CakkieBrown,
           modifier = Modifier.clickable {  }
       )
            }
        }
    }
}


























@Composable
fun DashDivider(
    thickness: Dp,
    color: Color,
    phase: Float = 10f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    modifier: Modifier
){
    Canvas(modifier = Modifier) {
        val dividerHeight = thickness.toPx()
        drawRoundRect(
            color = color,
            style =  Stroke(
                width = dividerHeight,
                pathEffect = PathEffect.dashPathEffect(
                    intervals,
                    phase
                )
            )
        )
    }
}
