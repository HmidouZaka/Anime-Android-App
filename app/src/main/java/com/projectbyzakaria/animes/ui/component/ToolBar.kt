package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.theme.black
import com.projectbyzakaria.animes.ui.theme.white

@Composable
fun ToolBar(
    onClickBack: () -> Unit,
    onClickSave: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier
            .padding(10.dp)
            .background(Color(0xFFFFFF))) {
        Icon(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = "",
            modifier = Modifier
                .padding(5.dp)
                .size(24.dp)
                .clickable { onClickBack() },
            tint = black,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "",
            modifier = Modifier
                .padding(5.dp)
                .size(26.dp)
                .clickable { onClickSave() },
            tint = black,
        )
    }


}


@Preview
@Composable
fun ToolBarPreview() {
    ToolBar(onClickBack = { /*TODO*/ }, onClickSave = { /*TODO*/ })
}