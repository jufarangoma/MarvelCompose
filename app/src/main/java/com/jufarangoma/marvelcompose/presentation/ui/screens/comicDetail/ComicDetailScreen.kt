package com.jufarangoma.marvelcompose.presentation.ui.screens.comicDetail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jufarangoma.marvelcompose.R
import com.jufarangoma.marvelcompose.domain.entities.Comic
import com.jufarangoma.marvelcompose.presentation.ui.screens.comics.ErrorView
import com.jufarangoma.marvelcompose.presentation.ui.screens.comics.Loading
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicDetailState
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.ComicDetailViewModel

@Composable
fun ComicDetailScreen(
    comicDetailViewModel: ComicDetailViewModel,
    comicId: Long
) {
    val comicDetail by comicDetailViewModel.comicDetailState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        comicDetailViewModel.getComicDetail(comicId)
    }
    AnimatedContent(
        comicDetail,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(3000)
            ) togetherWith fadeOut(animationSpec = tween(3000))
        }, label = "comicState"
    ) {
        when (it) {
            is ComicDetailState.Loading -> Loading()
            is ComicDetailState.Success -> ComicDetail(comic = it.comic)
            is ComicDetailState.Error -> ErrorView((it as ComicStates.Error).message)
            is ComicDetailState.EmptyComic -> Text(text = stringResource(id = R.string.null_comic))
        }
    }
}

@Composable
fun ComicDetail(comic: Comic) {
    LazyColumn(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Text(
                text = comic.title,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(comic.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        item {
            val description = if(comic.description.isNullOrEmpty()){
                stringResource(id = R.string.null_description)
            } else {
                comic.description
            }
            Text(
                text = description,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Justify

            )
        }
    }
}