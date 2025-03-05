package com.example.wedonate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.wedonate.ui.theme.WeDonateTheme
import com.example.wedonate.utils.NotificationHelper
import com.example.wedonate.viewmodel.DonationViewModel
import com.example.wedonate.viewmodel.DonationViewModelFactory
import kotlinx.coroutines.delay

class DonatePageActivity : ComponentActivity() {
    private val donationViewModel: DonationViewModel by viewModels { DonationViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeDonateTheme {
                DonationPageUI(donationViewModel)
            }
        }
    }
}

@Composable
fun DonationPageUI(viewModel: DonationViewModel) {
    val context = LocalContext.current
    var itemName by remember { mutableStateOf("") }
    var showAnimation by remember { mutableStateOf(false) }

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
                        viewModel.addDonationPoints(context,10) // Add 10 points per donation
                        Toast.makeText(context, "Thank you for donating!", Toast.LENGTH_SHORT).show()
                        showAnimation = true // Trigger animation
                    } else {
                        Toast.makeText(context, "Please enter an item name", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Donate")
            }

            if (showAnimation) {
                CelebrationAnimation { showAnimation = false }
            }
        }
    }
}

@Composable
fun CelebrationAnimation(onAnimationEnd: () -> Unit) {
    var isVisible by remember { mutableStateOf(true) }

    // Hide animation after 2 seconds
    LaunchedEffect(Unit) {
        delay(2000)
        isVisible = false
        onAnimationEnd()
    }

    if (isVisible) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Star Animation
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(500)) + scaleIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color.Yellow,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("+10 Points", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Yellow)
                }
            }

            // Confetti Animation (Using Lottie)
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
            LottieAnimation(
                composition = composition,
                iterations = 1, // Play once
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
