package com.example.moviesapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.example.moviesapp.view.Shows
import com.example.moviesapp.viewmodel.ShowsViewModel
import com.example.moviesapp.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movieId: String,
    viewModel: ShowsViewModel,
    onBack: () -> Unit
) {
    val showsState by viewModel.showsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            when (showsState) {
                is Result.Idle -> {
                    // Show nothing or placeholder
                    Text("Select a show", style = MaterialTheme.typography.bodyLarge)
                }
                is Result.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 100.dp))
                }
                is Result.Error -> {
                    Text(
                        text = (showsState as Result.Error).msg,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 100.dp)
                    )
                }
                is Result.Success -> {
                    val show: Shows? = (showsState as Result.Success<List<Shows>>).data
                        .find { it.id == movieId }

                    if (show == null) {
                        Text("Show not found", style = MaterialTheme.typography.bodyLarge)
                        return@Box
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Poster
                        Image(
                            painter = rememberAsyncImagePainter(show.poster),
                            contentDescription = show.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Title
                        Text(
                            text = show.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Year & Type
                        Text("Year: ${show.year}", style = MaterialTheme.typography.bodyMedium)
                        Text("Type: ${show.type}", style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Genres
                        //Text("Genres:", style = MaterialTheme.typography.titleMedium)
                        if (show.genre_names.isNotEmpty()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                show.genre_names.forEach { genre ->
                                    Text("$genre ", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        } else {
                            Text("Genre Unknown", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
