package com.example.wedonate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wedonate.ui.theme.WeDonateTheme
import com.example.wedonate.utils.DonationManager
import com.example.wedonate.utils.NotificationHelper

class DonationPageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeDonateTheme {
                DonationPageUI()
            }
        }
    }
}

@Composable
fun DonationPageUI() {
    val context = LocalContext.current
    var itemName by remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Donate an Item", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Item Name") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (itemName.isNotEmpty()) {
                        NotificationHelper.showDonationNotification(context)
                        DonationManager.addDonationPoints(context, 10) // Add 10 points per donation
                        Toast.makeText(context, "Thank you for donating!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please enter an item name", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Donate")
            }
        }
    }
}
