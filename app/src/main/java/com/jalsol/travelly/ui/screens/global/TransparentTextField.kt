package com.jalsol.travelly.ui.screens.global

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jalsol.travelly.ui.theme.teal
import com.jalsol.travelly.ui.theme.textColor

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    prefix: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        prefix = prefix,
        singleLine = true,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp.toPx())),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedLabelColor = textColor(isSystemInDarkTheme()),
            unfocusedLabelColor = textColor(isSystemInDarkTheme()),
            focusedIndicatorColor = teal(!isSystemInDarkTheme()),
            focusedPrefixColor = teal(!isSystemInDarkTheme()),
            unfocusedPrefixColor = textColor(isSystemInDarkTheme()),
            focusedTextColor = teal(!isSystemInDarkTheme()),
            unfocusedTextColor = textColor(isSystemInDarkTheme()),
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun TransparentTextFieldTest() {
    var text by remember { mutableStateOf("something") }
    TransparentTextField(
        value = text,
        onValueChange = { text = it },
        prefix = {
            Icon(
                imageVector = Icons.Outlined.Face,
                contentDescription = "Face"
            )
        }
    )
}
