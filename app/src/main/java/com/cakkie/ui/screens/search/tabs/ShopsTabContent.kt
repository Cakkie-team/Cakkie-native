package com.cakkie.ui.screens.search.tabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cakkie.data.db.models.ShopModel
import com.cakkie.ui.screens.shop.ShopItem
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ShopsTabContent(
    items: List<ShopModel>,
    listState: LazyListState,
    navigator: DestinationsNavigator
) {
    if (items.isEmpty()) {
        NoResultContent()
    } else {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(items) { item ->
                ShopItem(item = item, onClick = {})
            }
        }
    }
}