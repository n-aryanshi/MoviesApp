package com.example.moviesapp.view.screens

import android.R.attr.title
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.moviesapp.view.Shows
import com.example.moviesapp.viewmodel.ShowsViewModel
import com.example.moviesapp.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ShowsViewModel = hiltViewModel(),
    navController: NavController
) {

    val showsState by viewModel.showsState.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()

    // Fetch default tab (Movies) on first load
    LaunchedEffect(currentTab) {
        viewModel.fetchShows(currentTab)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Binge-Watching")
                }
            )
        }
    ){padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            // Tab toggle row
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { viewModel.fetchShows("movie") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentTab == "movie") Color.Gray else Color.LightGray
                    ),
                    modifier = Modifier.weight(1f)
                ) { Text("Movies") }

                Button(
                    onClick = { viewModel.fetchShows("tv_series") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentTab == "tv_series") Color.Gray else Color.LightGray
                    ),
                    modifier = Modifier.weight(1f)
                ) { Text("TV Shows") }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Show content based on Result state
            when (showsState) {
                is Result.Idle -> {}
                is Result.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is Result.Success -> {
                    val shows = (showsState as Result.Success<List<Shows>>).data
                    LazyColumn {
                        items(shows) { show ->
                            ShowItem(show = show) {
                                navController.navigate("details/${show.id}") // Or pass ID
                            }
                        }
                    }
                }
                is Result.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = (showsState as Result.Error).msg)
                    }
                }
            }
        }

    }

}

@Composable
fun ShowItem(show: Shows, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        // Poster image
        Image(
            painter = rememberAsyncImagePainter(show.poster),
            contentDescription = show.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Title text
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = show.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = show.year.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
