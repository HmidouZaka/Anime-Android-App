package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.theme.blue
import com.projectbyzakaria.animes.ui.theme.white
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Composable
fun FActionBottom(onClick:()->Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.padding(top = 0.dp, end = 8.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = blue
    ) {
        Icon(
            painter = painterResource(id = R.drawable.filter_icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = white
        )
    }
}