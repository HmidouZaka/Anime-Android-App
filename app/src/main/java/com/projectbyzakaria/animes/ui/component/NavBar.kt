package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectbyzakaria.animes.model.NavBarData

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    navBarData: NavBarData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        navBarData.favorites?.let {
            ItemInfo(com.projectbyzakaria.animes.R.drawable.like_icon, it)
            Spacer(modifier = Modifier.width(20.dp))
        }
        navBarData.members?.let {
            ItemInfo(com.projectbyzakaria.animes.R.drawable.card_membership_icon, it)
            Spacer(modifier = Modifier.width(20.dp))
        }
        navBarData.duration?.let {
            ItemInfo(com.projectbyzakaria.animes.R.drawable.time, it)
            Spacer(modifier = Modifier.weight(1f))
        }
        navBarData.date?.let {
            ItemInfo(com.projectbyzakaria.animes.R.drawable.date, it)
            Spacer(modifier = Modifier.weight(1f))
        }

        navBarData.score?.let {
            Column(
                modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = com.projectbyzakaria.animes.R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = it,
                    modifier = Modifier.padding(0.5.dp),
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

    }
}


@Composable
fun ItemInfo(icon: Int, type: String, modifier: Modifier = Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = type,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colors.onBackground
        )
        Text(
            text = type,
            modifier = Modifier.padding(0.5.dp),
            fontFamily = MaterialTheme.typography.h4.fontFamily,
            color = MaterialTheme.colors.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light
        )
    }
}