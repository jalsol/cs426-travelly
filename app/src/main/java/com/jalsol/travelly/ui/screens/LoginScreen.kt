package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.domain.serializer.dataStore
import com.jalsol.travelly.ui.Routes
import com.jalsol.travelly.ui.screens.global.CustomTextField
import com.jalsol.travelly.ui.screens.global.toPx
import com.jalsol.travelly.ui.theme.peach
import com.jalsol.travelly.ui.theme.teal
import com.jalsol.travelly.ui.theme.textColor
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navHostController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            colorFilter = ColorFilter.tint(textColor(isSystemInDarkTheme()))
        )
        Text(
            text = "Welcome back!",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = textColor(isSystemInDarkTheme())
        )
        Text(
            text = "Sign in and let's get going",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = textColor(isSystemInDarkTheme())
        )

        Spacer(modifier = Modifier.height(50.dp))

        CustomTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
                    color = textColor(isSystemInDarkTheme())
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "Password",
                    color = textColor(isSystemInDarkTheme())
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp.toPx()),
            colors = ButtonDefaults.buttonColors(
                containerColor = peach(dark = true),
                contentColor = textColor(!isSystemInDarkTheme())
            ),
            onClick = {
                scope.launch {
                     navHostController.context.dataStore.updateData {
                         it.copy(loggedIn = true)
                     }
                }
                navHostController.navigate(Routes.Home)
            }
        ) {
            Text(
                text = "Sign in",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            Text(
                text = "New?",
                fontSize = 16.sp,
                color = textColor(isSystemInDarkTheme())
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sign up",
                fontSize = 16.sp,
                color = teal(!isSystemInDarkTheme()),
                modifier = Modifier.clickable(
                    onClick = {
                        navHostController.navigate(Routes.Signup)
                    }
                )
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenPreview() {
    val navHostController = rememberNavController()
    LoginScreen(navHostController = navHostController)
}
