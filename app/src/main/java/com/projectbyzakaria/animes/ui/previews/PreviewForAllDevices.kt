package com.projectbyzakaria.animes.ui.previews

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "light mode normal device", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "light mode normal device", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "light mode tablet device", uiMode = UI_MODE_NIGHT_NO, showBackground = true, device = Devices.TABLET)
@Preview(name = "light mode tablet device", uiMode = UI_MODE_NIGHT_YES, showBackground = true, device = Devices.TABLET)
annotation class PreviewForAllDevices()
