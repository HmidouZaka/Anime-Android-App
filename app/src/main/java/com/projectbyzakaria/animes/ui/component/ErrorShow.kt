package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.theme.blue

@Composable
fun ErrorShow(
    error: String,
    isImageShown: Boolean = true,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    image: Int = R.drawable.error
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isImageShown){
            Image(
                painter = painterResource(id = image),
                contentDescription = "error image",
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(10.dp))
            )
        }
        Text(text = error, textAlign = TextAlign.Center,modifier=Modifier.padding(top = 8.dp))
        onClick?.let {
            Button(onClick = it, colors = ButtonDefaults.buttonColors(backgroundColor = blue)) {
                Text(text = "Refresh")
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    ErrorShow(error = "no iNternet", onClick = { /*TODO*/ }, image = R.drawable.finish)
}