package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.theme.AnimesTheme
import com.projectbyzakaria.animes.ui.theme.gray
import com.projectbyzakaria.animes.ui.view_models.StateUiViewModel

@Composable
fun ShowDialog(
    title: String,
    image: Int,
    content: String,
    textButtonNegative: String,
    textButtonPositefe: String,
    onClickBtnNegative: (() -> Unit)? = null,
    onClickBtnPositefe: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    viewModel: StateUiViewModel
) {
    if (viewModel.dialogIsShowIt){
        Dialog(
            onDismissRequest = { viewModel.hideDialog() }
        ) {
            Column(
                modifier.clip(CutCornerShape(10.dp)).background(MaterialTheme.colors.background).padding(vertical = 8.dp, horizontal = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop

                )
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = content)
                Row(modifier = Modifier.wrapContentSize()) {

                    Button(onClick = { onClickBtnPositefe?.let { it() } }) {
                        Text(text = textButtonPositefe)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { onClickBtnNegative?.let { it() } }, colors = ButtonDefaults.buttonColors(backgroundColor = gray)) {
                        Text(text = textButtonNegative)
                    }
                }

            }

        }
    }
}
