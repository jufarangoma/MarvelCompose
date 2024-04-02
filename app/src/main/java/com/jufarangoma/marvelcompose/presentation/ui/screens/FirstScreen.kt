package com.jufarangoma.marvelcompose.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jufarangoma.marvelcompose.R
import com.jufarangoma.marvelcompose.presentation.navigation.Screens

@Composable
fun FirstScreen(
    navigateToScreen: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stringResource(id = R.string.app_name),
            modifier = Modifier.fillMaxWidth(),
            fontSize = 36.sp,
            textAlign = TextAlign.Center
        )
        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
            onClick = { navigateToScreen(Screens.HeroesScreen.name) }) {
            Text(
                stringResource(id = R.string.start),
            )
        }
    }
}