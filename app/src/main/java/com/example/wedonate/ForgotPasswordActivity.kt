package com.example.wedonate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.wedonate.ui.theme.WeDonateTheme
import androidx.compose.ui.tooling.preview.Preview

class ForgotPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeDonateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ForgotPasswordScreen(
                        onResetClick = { /* Handle Password Reset Logic */ },
                        onBackToLoginClick = { startActivity(Intent(this, LoginActivity::class.java)) }
                    )
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(onResetClick: () -> Unit, onBackToLoginClick: () -> Unit) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Forgot Password?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your email and we will send you a password reset link.",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onResetClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "RESET PASSWORD", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Back to Login",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(onClick = onBackToLoginClick)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    WeDonateTheme {
        ForgotPasswordScreen(
            onResetClick = {},
            onBackToLoginClick = {}
        )
    }
}
