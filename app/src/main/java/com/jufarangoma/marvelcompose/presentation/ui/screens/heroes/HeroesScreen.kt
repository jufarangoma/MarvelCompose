package com.jufarangoma.marvelcompose.presentation.ui.screens.heroes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jufarangoma.marvelcompose.R
import com.jufarangoma.marvelcompose.domain.entities.Hero
import com.jufarangoma.marvelcompose.presentation.navigation.Screens
import com.jufarangoma.marvelcompose.presentation.ui.screens.comics.ErrorView
import com.jufarangoma.marvelcompose.presentation.ui.screens.comics.Loading
import com.jufarangoma.marvelcompose.presentation.ui.states.HeroStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.HeroesViewModel

@Composable
fun HeroesScreen(
    heroesViewModel: HeroesViewModel,
    navigateToScreen: (String) -> Unit
) {
    val heroesStates by heroesViewModel.heroStates.collectAsStateWithLifecycle()
    val textState = remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(Unit) {
        heroesViewModel.getHeroes()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchHero(
            heroesViewModel,
            textState
        )

        AnimatedContent(
            heroesStates,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(3000)
                ) togetherWith fadeOut(animationSpec = tween(3000))
            }, label = "heroesState"
        ) {
            when (it) {
                is HeroStates.Loading -> Loading()
                is HeroStates.Success -> {
                    if (it.heroes.isEmpty()) {
                        Text(text = stringResource(id = R.string.empty_list_heroes))
                    } else {
                        ListHeroes(heroes = it.heroes) {
                            navigateToScreen.invoke(Screens.ComicsScreen.name + it)
                        }
                    }
                }

                is HeroStates.Error -> ErrorView()
            }
        }
    }
}

@Composable
fun SearchHero(
    heroesViewModel: HeroesViewModel,
    textState: MutableState<TextFieldValue>
) {

    TextField(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        value = textState.value,
        onValueChange = { textState.value = it },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        placeholder = { Text(text = "Escribe le nombre de tu heroe") },
        keyboardActions = KeyboardActions(
            onDone = {
                heroesViewModel.getHeroes(textState.value.text)
            }
        )
    )
}

@Composable
fun ListHeroes(
    heroes: List<Hero>,
    navigateToScreen: (String) -> Unit
) {
    LazyColumn {
        items(heroes) { hero ->
            HeroeItem(hero = hero) {
                navigateToScreen.invoke("/${it.name}" + "/${it.id}")
            }
        }
    }
}