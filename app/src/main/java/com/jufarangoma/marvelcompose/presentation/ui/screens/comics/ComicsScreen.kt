package com.jufarangoma.marvelcompose.presentation.ui.screens.comics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.ComicsViewModel

@Composable
fun ComicsScreen(
    comicsViewModel: ComicsViewModel,
    heroId: Long,
    heroName: String,
    navigateToScreen: (String) -> Unit
){
    val heroComicsState by comicsViewModel.comicsState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        comicsViewModel.getSuperHeroComics(heroId)
    }

    LazyColumn(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onSecondary,
                    )
                )
            )
    ) {
        item {
            Text(
                modifier = Modifier.padding(16.dp),
                text = heroName
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 16.dp)
            ) {
                AnimatedVisibility(
                    visible = heroComicsState is ComicStates.Loading,
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp),
                        strokeWidth = 2.dp
                    )
                }

                AnimatedVisibility(visible = heroComicsState is ComicStates.Success) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 8.dp, bottom = 8.dp)
                            .fillMaxWidth()
                            .heightIn(max = 400.dp),
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items((heroComicsState as ComicStates.Success).comics.orEmpty()) { comic ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.padding(end = 10.dp),
                                elevation = CardDefaults.cardElevation(5.dp),
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = comic.title ?: "",
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}