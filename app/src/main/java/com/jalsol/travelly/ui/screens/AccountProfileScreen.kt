package com.jalsol.travelly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.screens.global.CustomTextField
import com.jalsol.travelly.ui.screens.global.PhoneVisualTransformation

@Composable
fun AccountProfileScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(
                    modifier = Modifier.clickable(onClick = { navHostController.navigateUp() }),
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back Arrow"
                )
            }
            Text(
                text = "Personal Information",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .shadow(8.dp)
                .height(100.dp)
                .width(100.dp),
        )

        Text(
            text = "Victoria Yoker",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )

        var firstName by remember { mutableStateOf("Victoria") }
        var lastName by remember { mutableStateOf("Yoker") }
        var phone by remember { mutableStateOf("0960069420") }
        var email by remember { mutableStateOf("victoria.yoker@gmail.com") }

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
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFEA36B),
                contentColor = Color.White
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

@Composable
private fun OptionButton(
    resId: Int,
    label: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier.padding(all = 8.dp),
            tint = Color(0xFFFEA36B)
        )
        Text(
            text = label,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountProfileScreenPreview() {
    val navHostController = rememberNavController()
    AccountProfileScreen(navHostController = navHostController)
}