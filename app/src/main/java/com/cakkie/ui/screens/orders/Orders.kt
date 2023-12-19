package com.cakkie.ui.screens.orders

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.doublePreferencesKey
import com.cakkie.R
import com.cakkie.ui.components.CakkieFilter
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBlue
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieGreen
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.CakkieYellow
import com.cakkie.ui.theme.Error
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Orders() {
    var expanded by remember {
        mutableStateOf(false)
    }
    val cake = painterResource(id = R.drawable.cake1)
    val cupcake = painterResource(id = R.drawable.cake2)
    val cakecho = painterResource(id = R.drawable.cake3)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .clickable { },
                tint = CakkieBrown
            )
            Text(
                text = stringResource(id = R.string.orders),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 70.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
     CakkieFilter()
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cake, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 5.dp),
                    verticalArrangement = Arrangement.Top
                ){
                    Text(text = stringResource(id = R.string.velvet_cupcakes),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "12 May, 8:23 am",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Card(modifier = Modifier
                    .size(width = 140.dp, height = 32.dp)
                    .padding(start = 70.dp),
                        colors = CardDefaults.cardColors(
                        containerColor = CakkieBlue,
                    contentColor = Color.White
                )  ,
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.pending),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 5.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
                Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cake, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.velvet_cupcakes),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "12 May, 8:23 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown
                    )
                }
                Card(modifier = Modifier
                    .size(width = 140.dp, height = 32.dp)
                    .padding(start = 90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Error,
                        contentColor = Color.White
                    )            ,
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.declined),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cakecho, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.chocolate_cake),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "20 May, 10:50 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown
                    )
                }
                Card(modifier = Modifier
                    .size(width = 150.dp, height = 32.dp)
                    .padding(start = 90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CakkieYellow,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.in_progress),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cupcake, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.velvet_cupcakes),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "12 May, 8:23 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown
                    )
                }
                Card(modifier = Modifier
                    .size(width = 140.dp, height = 32.dp)
                    .padding(start = 90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CakkieGreen,
                        contentColor = Color.White
                    )         ,
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.completed),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cake, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.velvet_cupcakes),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "12 May, 8:23 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown
                    )
                }
                Card(modifier = Modifier
                    .size(width = 140.dp, height = 32.dp)
                    .padding(start = 90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CakkieBlue,
                        contentColor = Color.White
                    ),
                            shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.pending),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cake, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.velvet_cupcakes),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "12 May, 8:23 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown

                    )
                }
                Card(modifier = Modifier
                    .size(width = 140.dp, height = 32.dp)
                    .padding(start = 90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Error,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.declined),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cake, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.velvet_cupcakes),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "12 May, 8:23 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown

                    )
                }
                Card(modifier = Modifier
                    .size(width = 150.dp, height = 32.dp)
                    .padding(start = 90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CakkieYellow,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.in_progress),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cakecho, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.chocolate_cake),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "20 May, 10:50 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown
                    )
                }
                Card(modifier = Modifier
                    .size(width = 140.dp, height = 32.dp)
                    .padding(start = 90.dp),
                        colors = CardDefaults.cardColors(
                        containerColor = CakkieGreen,
                    contentColor = Color.White
                ),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.completed),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cupcake, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.velvet_cupcakes),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "12 May, 8:23 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown
                    )
                }
                Card(modifier = Modifier
                    .size(width = 150.dp, height = 32.dp)
                    .padding(start = 90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CakkieYellow,
                        contentColor = Color.White
                    )            ,
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = stringResource(id = R.string.in_progress),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = cake, contentDescription = "")
                Column(modifier = Modifier
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = stringResource(id = R.string.velvet_cupcakes),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "12 May, 8:23 am",
                        style = MaterialTheme.typography.bodySmall,
                        color = CakkieLightBrown
                    )
                }
                Card(modifier = Modifier
                    .size(width = 140.dp, height = 32.dp)
                    .padding(start = 90.dp),
                        colors = CardDefaults.cardColors(
                        containerColor = CakkieGreen,
                    contentColor = Color.White
                )     ,
                    shape = RoundedCornerShape(20)
                    ) {
                    Text(text = stringResource(id = R.string.completed),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
    }
}