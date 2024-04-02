package com.jufarangoma.marvelcompose.presentation.ui.screens.heroes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jufarangoma.marvelcompose.domain.entities.Hero
import com.jufarangoma.marvelcompose.presentation.models.HeroSerializable

@Composable
fun HeroeItem(
    hero: Hero,
    heroeClicked: (heroe: HeroSerializable) -> Unit
) {
    Card(
        modifier = Modifier
            .height(240.dp)
            .width(240.dp)
            .padding(8.dp)
            .clickable {
                heroeClicked(HeroSerializable(hero.id, hero.name))
            }
    ) {
        Column {
            Text(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                text = hero.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(hero.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}