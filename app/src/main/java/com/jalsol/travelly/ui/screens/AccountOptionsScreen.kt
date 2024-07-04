package com.jalsol.travelly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.Routes

@Composable
fun AccountOptionsScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Account",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )

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
        }

        Column {
            OptionButton(
                resId = R.drawable.logo_person_thick,
                label = "Personal information",
                onClick = { navHostController.navigate(Routes.Account.Profile) }
            )
            OptionButton(
                resId = R.drawable.logo_creditcard,
                label = "Payment and cards",
            ) { }
            OptionButton(
                resId = R.drawable.logo_heart,
                label = "Saved",
            ) { }
            OptionButton(
                resId = R.drawable.logo_history,
                label = "Booking history",
            ) { }
            OptionButton(
                resId = R.drawable.logo_settings,
                label = "Settings",
            ) { }
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
                containerColor = Color.White,
                contentColor = Color.Red
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo_exit),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "End session",
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
private fun AccountOptionsScreenPreview() {
    val navHostController = rememberNavController()
    AccountOptionsScreen(navHostController = navHostController)
}