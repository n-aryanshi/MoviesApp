package com.example.moviesapp

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.navigation.NavGraph
import com.example.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://google.com")
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 5000
                connection.connect()
                Log.d("NetworkTest", "✅ Internet works: ${connection.responseCode}")
            } catch (e: Exception) {
                Log.e("NetworkTest", "❌ Failed: ${e.message}")
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MoviesAppTheme {
//        Greeting("Android")
//    }
//}