package com.projectbyzakaria.animes.ui.component

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalDragOrCancellation
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.model.MovieLocal
import com.projectbyzakaria.animes.ui.theme.black
import com.projectbyzakaria.animes.ui.theme.fontFamily
import com.projectbyzakaria.animes.ui.theme.gray
import com.projectbyzakaria.animes.ui.theme.white
import com.projectbyzakaria.animes.utilt.TypeMovies
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@SuppressLint("ReturnFromAwaitPointerEventScope", "MultipleAwaitPointerEventScopes")
@OptIn(ExperimentalMaterialApi::class, ExperimentalTextApi::class)
@Composable
fun ItemMovieSave(
    local: MovieLocal,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
    onDismass: (MovieLocal) -> Unit
) {
    val offsetX = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    var color by remember {
        mutableStateOf(Color(0xFFFF0000).copy(alpha = 0f))
    }
    var positionX by remember {
        mutableStateOf(0f)
    }
    val textMeasurer = rememberTextMeasurer(50)
    Card(
        modifier = Modifier
            .fillMaxWidth(), onClick = onClickItem, elevation = 2.dp
    ) {
        Box(Modifier.drawBehind {
            drawRect(color)
            drawText(
                text = AnnotatedString("Delete", SpanStyle(color = white, fontFamily = fontFamily, fontSize = 30.sp)),
                textMeasurer =textMeasurer , topLeft = Offset(y=(size.height*0.5f) - (30.sp.toPx()*0.5f),x=positionX)
            )
        }) {
            Row(
                modifier = modifier
                    .pointerInput(Unit) pointerInput@{
                        val decay = splineBasedDecay<Float>(this)
                        coroutineScope {
                            while (true) {
                                offsetX.stop()
                                val velocityTracker = VelocityTracker()
                                var id = awaitPointerEventScope { awaitFirstDown().id }
                                awaitPointerEventScope {
                                    horizontalDrag(id) {

                                        val horizontalDragOffset =
                                            offsetX.value + it.positionChange().x
                                        val alpha = offsetX.value * 0.001f
                                        if (horizontalDragOffset in 0.0..400.0){
                                            positionX = horizontalDragOffset - 300f
                                        }else{
                                            if (horizontalDragOffset < 0 && horizontalDragOffset >= -500){
                                                positionX = horizontalDragOffset + size.width
                                            }
                                        }


                                        color = if (alpha > 0) color.copy(alpha = alpha) else color.copy(alpha * -1f)

                                        launch {
                                            offsetX.snapTo(horizontalDragOffset)
                                        }
                                        velocityTracker.addPosition(it.uptimeMillis, it.position)
                                        if (it.positionChange() != Offset.Zero) it.consume()
                                    }
                                }
                                val velocity = velocityTracker.calculateVelocity().x
                                val targetOffset =
                                    decay.calculateTargetValue(offsetX.value, velocity)
                                offsetX.updateBounds(
                                    lowerBound = -size.width.toFloat(),
                                    upperBound = size.width.toFloat()
                                )
                                launch {
                                    if (targetOffset.absoluteValue <= size.width) {
                                        offsetX.animateTo(
                                            targetValue = 0f,
                                            initialVelocity = velocity
                                        )
                                    } else {
                                        offsetX.animateDecay(velocity, decay)
                                        onDismass(local)
                                    }
                                }
                            }
                        }

                    }
                    .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    .background(MaterialTheme.colors.background)
            ) {
                Image(
                    bitmap = local.image.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(5.dp)
                        .width(130.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shadow(2.dp, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp)
                )
                {
                    Text(
                        text = local.name,
                        fontSize = 18.sp,
                        fontFamily = MaterialTheme.typography.h4.fontFamily,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colors.onBackground,
                        textDecoration = TextDecoration.Underline,
                        maxLines = 2,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = local.description,
                        fontSize = 14.sp,
                        fontFamily = MaterialTheme.typography.h4.fontFamily,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF646464),
                        textDecoration = TextDecoration.Underline,
                        maxLines = 8,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }


    }
}

