package com.projectbyzakaria.animes.ui.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.relotion.epsedies.Data
import com.projectbyzakaria.animes.data.dao.relotion.epsedies.Images
import com.projectbyzakaria.animes.data.dao.relotion.epsedies.Jpg
import com.projectbyzakaria.animes.ui.theme.gray

@Composable
fun EpsidesComponent(
    data: Data,
    onClick: (uel:String,title:String) -> Unit
) {
    var context = LocalContext.current
    Card(
        Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable {
                if (data.url != null) {
                    onClick(data.url, data.title ?: "UnKnow Title")
                } else {
                    Toast
                        .makeText(context, "No Url For this Episode", Toast.LENGTH_SHORT)
                        .show()
                }
            },
        shape = CutCornerShape(4.dp),
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(data.images.jpg.image_url)
            .crossfade(200)
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .build()
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                model =imageRequest ,
                contentDescription = null,
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxHeight()
                    .width(70.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), verticalArrangement = Arrangement.Center) {

                Text(
                    text = data.title ?: "No Title",
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = data.episode ?: "No Number Found",
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
            }


        }
    }


}

@Preview
@Composable
fun PreviewE() {
    EpsidesComponent(data = Data("Epside : 20", Images(Jpg("")), 0, "narouto", "")) {_,_->

    }
}