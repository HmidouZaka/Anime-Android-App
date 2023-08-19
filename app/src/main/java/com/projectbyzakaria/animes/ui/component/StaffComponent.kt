package com.projectbyzakaria.animes.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StaffComponent(
    staff:com.projectbyzakaria.animes.data.dao.characters_search.Data,
    onClick:()->Unit
) {
    CharacterCard(data = staff) {
        onClick()
    }
}