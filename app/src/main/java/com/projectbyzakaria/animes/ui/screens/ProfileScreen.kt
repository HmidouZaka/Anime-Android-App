package com.projectbyzakaria.animes.ui.screens

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.model.User
import com.projectbyzakaria.animes.ui.component.InputComponent
import com.projectbyzakaria.animes.ui.view_models.DataViewModel
import com.projectbyzakaria.animes.ui.view_models.StateUiViewModel
import com.projectbyzakaria.animes.utilt.isValidEmail


@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DataViewModel,
    onClickChangeImage: () -> Unit = {}
) {
    val image by viewModel.userImage.observeAsState(null)
    val name by viewModel.userName.observeAsState(null)
    val email by viewModel.userEmail.observeAsState(null)
    val follow by viewModel.userFollow.observeAsState(0)
    val favorites by viewModel.userFavorites.collectAsState()
    LaunchedEffect(key1 = false) {
        viewModel.getUser()
    }
    var infiniteAnimation = rememberInfiniteTransition()
    var backround = infiniteAnimation.animateColor(
        initialValue = Color(0x3CFF5DC4),
        targetValue = Color(0x3D33C0FF),
        animationSpec = infiniteRepeatable(tween(1550), repeatMode = RepeatMode.Reverse)
    )
    var circle = infiniteAnimation.animateColor(
        initialValue = Color(0x3CFF5DC4),
        targetValue = Color(0x3D33C0FF),
        animationSpec = infiniteRepeatable(tween(1550), repeatMode = RepeatMode.Reverse)
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
            .padding(bottom = 55.dp)

    ) {
        val drawable =
            (navHostController.context.getDrawable(R.drawable.error) as BitmapDrawable).bitmap
        Box(
            Modifier
                .fillMaxWidth()
                .height(450.dp)

        ) {
            Image(
                bitmap = image?.asImageBitmap() ?: drawable.asImageBitmap(),
                contentDescription = "Image",
                modifier = Modifier
                    .matchParentSize(),
                contentScale = ContentScale.Crop,
                alpha = 1f
            )
            Spacer(modifier = Modifier
                .matchParentSize()
                .drawBehind {

                    drawRect(backround.value)
                    drawCircle(
                        circle.value,
                        radius = 300.dp.toPx(),
                        center = center.copy(200f, -10f)
                    )
                    drawCircle(
                        circle.value,
                        radius = 350.dp.toPx(),
                        center = center.copy(500f, 10f)
                    )
                })
        }
        Column(
            Modifier
                .padding(top = 350.dp)
                .fillMaxSize()
                .smoothBackground(MaterialTheme.colors.background)

        ) {
            Spacer(modifier = Modifier.size(100.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = favorites.toString(), color = Color(0xFF26A9FF))
                    Text(text = "Favorites", color = Color(0xFF5F5F5F))
                }

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = follow.toString(), color = Color(0xFF26A9FF))
                    Text(text = "Followers", color = Color(0xFF5F5F5F))
                }
            }
            Spacer(modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(2.dp)
                .fillMaxWidth()
                .drawBehind {
                    drawRect(circle.value)
                })


            InputComponent(
                data = { name ?: "" },
                label = "Name",
                modifier = Modifier.fillMaxWidth(),
                viewModel = viewModel,
                onTextChanged = { viewModel.changeName(it) },
                onClickSave = {
                        viewModel.name = it
                        viewModel.upDateName(it)

                })
            InputComponent(
                data = { email ?: "" },
                label = "Email",
                modifier = Modifier.fillMaxWidth(),
                viewModel = viewModel,
                onTextChanged = {viewModel.changeEmail(it)},
                onClickSave = {
                    if (it.isValidEmail()){
                        viewModel.email = it
                        viewModel.upDateEmail(it)
                    }else{
                        Toast.makeText(navHostController.context, "Enter a valid Email", Toast.LENGTH_SHORT).show()
                    }
                }
            )

        }
        Image(
            bitmap = image?.asImageBitmap() ?: drawable.asImageBitmap(),
            contentDescription = "Image profile",
            modifier = Modifier
                .padding(top = 300.dp, start = 30.dp)
                .size(120.dp)
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colors.background, CircleShape)
                .clickable { onClickChangeImage() },
            contentScale = ContentScale.Crop,
            alpha = 1f
        )
    }
}


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.smoothBackground(color: Color) = composed {
    drawBehind {
        val brush = SolidColor(color)
        val path = Path().apply {
            moveTo(0f, size.height)
            lineTo(0f, 0f)
            cubicTo(
                x1 = 50f,
                y1 = 50f,
                x2 = size.width * 0.3f,
                y2 = 180f,
                x3 = size.width * 0.6f,
                y3 = 100f,
            )
            cubicTo(
                x1 = size.width * 0.6f,
                y1 = 100f,
                x2 = size.width * 0.9f,
                y2 = 20f,
                x3 = size.width,
                y3 = 10f,
            )
            lineTo(size.width, size.height)
        }
        drawPath(
            path = path,
            brush = brush
        )
    }
}