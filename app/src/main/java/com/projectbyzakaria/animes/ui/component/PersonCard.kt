package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.pepole.Data
import com.projectbyzakaria.animes.data.dao.pepole.Images
import com.projectbyzakaria.animes.data.dao.pepole.Jpg
import com.projectbyzakaria.animes.utilt.formatDate
import com.projectbyzakaria.animes.utilt.take

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonCard(
    dataPerson: Data,
    onClick: () -> Unit
) {
    val data = ImageRequest.Builder(LocalContext.current)
        .data(dataPerson.images.jpg.image_url)
        .crossfade(true)
        .error(R.drawable.error)
        .placeholder(R.drawable.loading)
        .build()

    Card(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(), shape = CutCornerShape(8.dp),
        onClick = { onClick() }, elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.background
    )
    {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp, max = 160.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .wrapContentSize()
                    .height(120.dp)
                    .width(100.dp),
                model = data,
                contentDescription = "null",
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .weight(1f)
                    .heightIn(min = 150.dp, max = 160.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                ) {
                    if (dataPerson.name != null) {
                        Text(
                            text = dataPerson.name.take(),
                            modifier = Modifier
                                .padding(start = 5.dp, top = 5.dp)
                                .weight(1f),
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                    if (dataPerson.birthday != null) {
                        Text(text = dataPerson.birthday.formatDate(), maxLines = 1, modifier = Modifier
                            .padding(start = 5.dp, top = 5.dp))
                    }
                }
                    Text(
                        text = dataPerson.about ?: "No Info",
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(5.dp)
                    )

            }
        }


    }
}





