package com.jufarangoma.marvelcompose.presentation.ui.screens.comics

import ComicList
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jufarangoma.marvelcompose.R
import com.jufarangoma.marvelcompose.presentation.navigation.Screens
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.ComicsViewModel

@Composable
fun ComicsScreen(
    comicsViewModel: ComicsViewModel,
    heroId: Long,
    heroName: String,
    navigateToScreen: (String) -> Unit
) {
    val heroComicsState by comicsViewModel.comicsState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        comicsViewModel.getComics(heroId)
    }

    Column {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            text = heroName,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        AnimatedContent(
            heroComicsState,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(3000)
                ) togetherWith fadeOut(animationSpec = tween(3000))
            }, label = "heroComicState"
        ) {
            when (it) {
                is ComicStates.Loading -> Loading()
                is ComicStates.Success -> ComicList(heroComicsState = heroComicsState) {
                    navigateToScreen.invoke(Screens.ComicDetailScreen.name + "/$it")
                }

                is ComicStates.Error -> ErrorView((heroComicsState as ComicStates.Error).message)
            }
        }
    }

}

@Composable
fun ErrorView(message: String? = null) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = message ?: stringResource(id = R.string.default_error_message)
    )
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .fillMaxSize(),

            color = MaterialTheme.colorScheme.onPrimaryContainer,
            strokeWidth = 2.dp,
        )
    }
}