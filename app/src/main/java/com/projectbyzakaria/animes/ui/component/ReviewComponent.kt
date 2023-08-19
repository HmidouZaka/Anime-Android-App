package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.projectbyzakaria.animes.data.dao.relotion.reviews.*
import com.projectbyzakaria.animes.ui.theme.AnimesTheme
import com.projectbyzakaria.animes.ui.theme.blue
import com.projectbyzakaria.animes.ui.theme.gray
import com.projectbyzakaria.animes.ui.theme.pink
import com.projectbyzakaria.animes.utilt.take
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReviewComponent(
    review: Data,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onClickSend:()->Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.background)
            .padding(2.dp)
    ) {
        UserComponent(user = review.user, date = review.date ?: "No Review")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(gray)
        )
        TextOverView(text = review.review ?: "Review", Modifier.background(Color(0xCDECECEC)).padding(horizontal = 8.dp), 12.sp)
        Row(modifier = Modifier) {
            ItemReview(R.drawable.like, review.reactions.nice ?: 0, null, Modifier.weight(1f))
            ItemReview(
                R.drawable.favorite_like,
                review.reactions.love_it ?: 0,
                R.drawable.baseline_favorite_24,
                Modifier.weight(1f)
            )

        }
    }


}


@Preview
@Composable
fun ShowReview() {
    val data = Data(
        "2008-04-27T04:51:00+00:00",
        0,
        false,
        false,
        0,
        Reactions(
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        ), "",
        0,
        listOf(),
        "",
        "",
        User(
            Images(
                Jpg(""), Webp("")
            ), "https//com.google.com",
            "Zakaria"
        )
    )
    ReviewComponent(data,Modifier,{}) {

    }
}


@Preview
@Composable
fun ShowUser() {
    AnimesTheme {
        UserComponent(
            User(
                Images(
                    Jpg(""), Webp("")
                ), "https//com.google.com",
                "Zakaria"
            ), "2008-04-27T04:51:00+00:00"
        )
    }
}

@Composable
fun UserComponent(user: User, date: String) {

    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colors.background)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        val context = LocalContext.current
        var imageRequires = ImageRequest.Builder(context)
            .data(user.images.jpg.image_url)
            .crossfade(200)
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .build()
        AsyncImage(
            model = imageRequires,
            contentDescription = "user Profile",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = user.username ?: "No Name",
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = MaterialTheme.typography.h6.fontFamily
            )
            Text(
                text = user.url?.take(size = 20) ?: "No url",
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = blue,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                fontFamily = MaterialTheme.typography.subtitle2.fontFamily
            )
        }
        val dateR = simpleDateFormat.parse(date)
        Text(
            text = "${dateR.year}-${dateR.month}-${dateR.day}",
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onBackground,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = MaterialTheme.typography.h1.fontFamily
        )
    }
}

@Composable
fun ItemReview(icon: Int, number: Int, iconIfSelect: Int? = null, modifier: Modifier = Modifier,onClick: (() -> Unit)?=null) {
    val isChecked = rememberSaveable {
        mutableStateOf(false)
    }
    val likeNumber = if (isChecked.value) number + 1 else number
    val color = if (isChecked.value ) if (icon == R.drawable.favorite_like) pink else blue else Color.Unspecified
    val iconSelect  = if (isChecked.value && icon ==R.drawable.favorite_like)
            painterResource(id = iconIfSelect!!)
    else painterResource(id = iconIfSelect ?: icon)
    IconToggleButton(
        modifier = modifier.height(32.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it;onClick?.let { it1 -> it1() } }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = iconSelect,
                contentDescription = "like",
                modifier = Modifier.size(18.dp),
                tint = color
            )
            if (likeNumber >= 0 && icon != R.drawable.baseline_send_24) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = likeNumber.toString(), fontSize = 14.sp , color = if (isChecked.value) blue else MaterialTheme.colors.onBackground)
            }

        }

    }
}
