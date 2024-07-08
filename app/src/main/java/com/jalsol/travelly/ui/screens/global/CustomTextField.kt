package com.jalsol.travelly.ui.screens.global

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jalsol.travelly.ui.theme.backgroundColor
import com.jalsol.travelly.ui.theme.textColor

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    prefix: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    label: @Composable (() -> Unit),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        prefix = prefix,
        modifier = modifier.clip(RoundedCornerShape(10.dp.toPx())),
        readOnly = readOnly,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor(isSystemInDarkTheme()),
            unfocusedContainerColor = backgroundColor(isSystemInDarkTheme()),
            disabledContainerColor = backgroundColor(isSystemInDarkTheme()),
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            disabledLabelColor = Color.Gray,
            focusedTextColor = textColor(isSystemInDarkTheme()),
            unfocusedTextColor = textColor(isSystemInDarkTheme()),
            disabledTextColor = textColor(isSystemInDarkTheme()),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedPrefixColor = textColor(isSystemInDarkTheme()),
            unfocusedPrefixColor = textColor(isSystemInDarkTheme()),
            disabledPrefixColor = textColor(isSystemInDarkTheme())
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CustomTextFieldTest() {
    var text by remember { mutableStateOf("something") }
    CustomTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(text = "Your Label Here") },
        readOnly = true,
        enabled = false
    )
}

// https://stackoverflow.com/a/74526322

class PhoneVisualTransformation(private val mask: String, private val maskNumber: Char) :
    VisualTransformation {
    private val maxLength = mask.count { it == maskNumber }
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        return maskNumber == other.maskNumber
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}