package com.example.responsivedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.responsivedemo.ui.theme.ResponsiveDemoTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResponsiveDemoTheme {
                ResponsiveLayout()
            }
        }
    }
}

@Composable
fun ResponsiveLayout(windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass) {
    val windowWidthSizeClass = windowSizeClass.windowWidthSizeClass
    BoxWithConstraints {
        Log.d("ResponsiveLayout", "maxWidth: $maxWidth")
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.images),
                    contentDescription = "Logo"
                )
                if (windowWidthSizeClass == WindowWidthSizeClass.MEDIUM) {
                    Text("Logo and some text", modifier = Modifier.padding(start = 8.dp))
                }else if (windowWidthSizeClass == WindowWidthSizeClass.EXPANDED){
                    Text("Logo and looooooots of text", modifier = Modifier.padding(start = 8.dp))
                }
            }
            SearchBar(windowWidthSizeClass)
        }
    }
}

@Composable
fun SearchBar(windowWidthSizeClass: WindowWidthSizeClass) {
    when (windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            IconButton(onClick = {}) {
                Icon(painter = painterResource(id = R.drawable.glass), contentDescription = "Search")
            }
        }
        WindowWidthSizeClass.MEDIUM -> {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = R.drawable.glass), contentDescription = "Search")
                }
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Search") },
                    modifier = Modifier.width(200.dp)
                )
            }
        }
        else -> {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(id = R.drawable.glass), contentDescription = "Search")
                }
                TextField(
                    value = "",
                    onValueChange = { },
                    placeholder = { Text("Search") },
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ResponsiveDemoTheme {
        ResponsiveLayout()
    }
}