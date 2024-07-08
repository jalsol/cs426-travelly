package com.jalsol.travelly.ui.screens.home

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jalsol.travelly.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MenuSearchBar() {
    var query by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = query,
        onQueryChange = { query = it },
        active = false,
        onActiveChange = {},
        placeholder = { Text("Search") },
        onSearch = {},
        shape = RoundedCornerShape(45),
        content = {},
        colors = SearchBarDefaults.colors(
            containerColor = if (isSystemInDarkTheme()) {
                Color.Black
            } else {
                Color.White
            }
        ),
        trailingIcon = {
            Button(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(40),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFEA36B)
                ),
                onClick = {
                    Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = textColor(!isSystemInDarkTheme())
                )
            }
        }
    )
}
