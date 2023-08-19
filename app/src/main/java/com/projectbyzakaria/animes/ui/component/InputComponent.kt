package com.projectbyzakaria.animes.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectbyzakaria.animes.ui.theme.AnimesTheme
import com.projectbyzakaria.animes.ui.theme.white
import com.projectbyzakaria.animes.ui.view_models.DataViewModel

@Composable
fun InputComponent(
    data: () -> String,
    viewModel: DataViewModel,
    modifier: Modifier = Modifier,
    label: String = "Name",
    onTextChanged: (String) -> Unit,
    onClickSave: (String) -> Unit
) {
    var isTextChanged = rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = data(),
            onValueChange = {
                onTextChanged(it)
                if (it != if (label == "Name") viewModel.name else viewModel.email){
                    if (!isTextChanged.value){
                        isTextChanged.value = false
                    }
                    isTextChanged.value = true
                }else{
                    if (isTextChanged.value){
                        isTextChanged.value = false
                    }
                }
                 },
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 10.dp, vertical = 8.dp),
            maxLines = 1,
            singleLine = true,
            label = { Text(text = label, fontSize = 16.sp) },
            shape = RoundedCornerShape(8.dp)
        )
        AnimatedVisibility(visible = isTextChanged.value) {
            Button(
                onClick = {
                    isTextChanged.value = false
                    onClickSave(data())
                          },
                modifier = Modifier
                    .padding(8.dp)
                    .width(70.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF01CC3A))
            ) {
                Text(text = "Save", color = white)
            }
        }
    }
}


