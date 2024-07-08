package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.domain.model.Base64StringToImage
import com.jalsol.travelly.domain.model.Preferences
import com.jalsol.travelly.domain.serializer.dataStore
import com.jalsol.travelly.ui.screens.global.CustomTextField
import com.jalsol.travelly.ui.screens.global.HeaderBar
import com.jalsol.travelly.ui.screens.global.PhoneVisualTransformation
import com.jalsol.travelly.ui.theme.peach
import com.jalsol.travelly.ui.theme.textColor
import kotlinx.coroutines.launch

@Composable
fun AccountProfileScreen(navHostController: NavHostController) {
    val dataStore = navHostController.context.dataStore
    val preferences = dataStore.data.collectAsState(
        initial = Preferences()
    ).value
    val scope = rememberCoroutineScope()
    var firstName by remember { mutableStateOf(preferences.firstName) }
    var lastName by remember { mutableStateOf(preferences.lastName) }
    var phone by remember { mutableStateOf(preferences.phone) }
    var email by remember { mutableStateOf(preferences.email) }
    LaunchedEffect(preferences) {
        firstName = preferences.firstName
        lastName = preferences.lastName
        phone = preferences.phone
        email = preferences.email
    }
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderBar(
            navHostController = navHostController,
            title = "Personal Information"
        )

        Box {
            Base64StringToImage(
                base64String = preferences.avatarBase64,
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(8.dp)
                    .height(100.dp)
                    .width(100.dp),
            )

            Image(
                painter = painterResource(id = R.drawable.logo_addimage),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }

        Text(
            text = "${preferences.firstName} ${preferences.lastName}",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = textColor(isSystemInDarkTheme())
        )

        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
            CustomTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
            CustomTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                visualTransformation = PhoneVisualTransformation("000 000 0000", '0')
            )
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                scope.launch {
                    dataStore.updateData {
                        it.copy(
                            firstName = firstName,
                            lastName = lastName,
                            phone = phone,
                            email = email
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = peach(isSystemInDarkTheme()),
                contentColor = textColor(!isSystemInDarkTheme())
            )
        ) {
            Text(
                text = "Save changes",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AccountProfileScreenPreview() {
    val navHostController = rememberNavController()
    AccountProfileScreen(navHostController = navHostController)
}