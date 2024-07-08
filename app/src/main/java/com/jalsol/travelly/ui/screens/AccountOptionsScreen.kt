package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.domain.model.Base64StringToImage
import com.jalsol.travelly.domain.model.Preferences
import com.jalsol.travelly.domain.serializer.dataStore
import com.jalsol.travelly.ui.Routes
import com.jalsol.travelly.ui.theme.textColor

@Composable
fun AccountOptionsScreen(navHostController: NavHostController) {
    val preferences = navHostController.context.dataStore.data.collectAsState(
        initial = Preferences()
    ).value
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
                fontSize = 18.sp,
                color = textColor(isSystemInDarkTheme())
            )

            Base64StringToImage(
                base64String = preferences.avatarBase64,
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(8.dp)
                    .height(100.dp)
                    .width(100.dp),
            )

            Text(
                text = "${preferences.firstName} ${preferences.lastName}",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = textColor(isSystemInDarkTheme())
            )
        }

        Column {
            val context = LocalContext.current
            OptionButton(
                resId = R.drawable.logo_person_thick,
                label = "Personal information",
                onClick = { navHostController.navigate(Routes.Account.Profile) }
            )
            OptionButton(
                resId = R.drawable.logo_creditcard,
                label = "Payment and cards",
                onClick = { Toast.makeText(context, "Will be developed", Toast.LENGTH_SHORT).show() }
            )
            OptionButton(
                resId = R.drawable.logo_heart,
                label = "Saved",
                onClick = { Toast.makeText(context, "Will be developed", Toast.LENGTH_SHORT).show() }
            )
            OptionButton(
                resId = R.drawable.logo_history,
                label = "Booking history",
                onClick = { Toast.makeText(context, "Will be developed", Toast.LENGTH_SHORT).show() }
            )
            OptionButton(
                resId = R.drawable.logo_settings,
                label = "Settings",
                onClick = { Toast.makeText(context, "Will be developed", Toast.LENGTH_SHORT).show() }
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
            color = textColor(isSystemInDarkTheme())
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AccountOptionsScreenPreview() {
    val navHostController = rememberNavController()
    AccountOptionsScreen(navHostController = navHostController)
}