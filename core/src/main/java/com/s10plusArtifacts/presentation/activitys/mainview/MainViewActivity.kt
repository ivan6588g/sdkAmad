package com.s10plusArtifacts.presentation.activitys.mainview

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.s10plusArtifacts.app.presentation.navigation.InitAmadCore
import com.s10plusArtifacts.presentation.activitys.mainview.ui.theme.AmadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainViewActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val token = intent.getStringExtra("token")
        val navView = intent.getBooleanExtra("navView",false)
        // Establecer orientación en vertical
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            AmadTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (token != null) {
                        InitAmadCore(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()), token = token, backView = navView )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    AmadTheme {
        Greeting2("Android")
    }
}