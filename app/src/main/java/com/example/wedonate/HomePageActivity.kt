package com.example.wedonate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wedonate.ui.theme.WeDonateTheme
import com.example.wedonate.utils.NotificationHelper
import com.example.wedonate.viewmodel.DonationViewModel
import com.example.wedonate.viewmodel.DonationViewModelFactory

class HomePageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createNotificationChannel(this)
        setContent {
            WeDonateTheme {
                val viewModel: DonationViewModel by viewModels { DonationViewModelFactory(this) }
                HomePageUI(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageUI(viewModel: DonationViewModel) {
    val context = LocalContext.current
    var isDonor by remember { mutableStateOf(true) }
    val donationPoints by viewModel.donationPoints.collectAsStateWithLifecycle(initialValue = 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WeDonate") },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "Donation Points", tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "$donationPoints", fontWeight = FontWeight.Bold)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.addDonationPoints(context, 10) // Adds 10 points per donation
                    context.startActivity(Intent(context, DonationPageActivity::class.java))
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Donation",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Toggle Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { isDonor = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDonor) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        contentColor = if (isDonor) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Donor")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { isDonor = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isDonor) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        contentColor = if (!isDonor) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Orphanage")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Welcome Message
            Text(
                text = if (isDonor) "Welcome, Donor! Add items to donate." else "Welcome, Orphanage! View donations available.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
