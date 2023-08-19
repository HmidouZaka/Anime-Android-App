package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.theme.gray
import com.projectbyzakaria.animes.utilt.runIfQueryNotEmpty

@Composable
fun SearchComponent(
    modifier: Modifier = Modifier,
    onSearchValueChanged: ((String) -> Unit)? = null,
    onClickSearch: ((String) -> Unit)? = null
) {

    var query = rememberSaveable {
        mutableStateOf("")
    }
    var textQuery = ""
    TextField(
        value = query.value,
        onValueChange = {
            query.value = it
            textQuery = it
            onSearchValueChanged?.let { it1 -> it1(it) }
        },
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.search), contentDescription = null)
        },
        trailingIcon = {
            if (query.value.isNotEmpty()){
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        query.value = ""
                    })
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colors.onBackground,
            disabledIndicatorColor = Color.Unspecified,
            focusedIndicatorColor = Color.Unspecified,
            unfocusedIndicatorColor = Color.Unspecified,
            errorIndicatorColor = Color.Unspecified
        ),
        keyboardActions = KeyboardActions(onSearch = { onClickSearch?.let { it(query.value)  } }),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        shape = RoundedCornerShape(8.dp),
        maxLines = 1,
        placeholder = {Text("Search")},
        singleLine = true

    )

}


@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchComponent()
}