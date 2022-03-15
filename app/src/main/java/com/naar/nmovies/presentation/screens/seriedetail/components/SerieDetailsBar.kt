package com.naar.nmovies.presentation.screens.seriedetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.naar.nmovies.presentation.screens.moviedetails.component.CategoriesGrid
import com.naar.nmovies.presentation.screens.moviedetails.component.HeaderTitle
import com.naar.nmovies.presentation.screens.moviedetails.component.Note

@Composable
fun SerieDetailsBar(
    modifier : Modifier = Modifier,
    image: String,
    title: String,
    note: String,
    categories: List<String>
) {
    Row(
        modifier = modifier
    ) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            HeaderTitle(title = title, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(6.dp))
            Note(note = note)
            Spacer(modifier = Modifier.height(6.dp))
            CategoriesGrid(categories = categories)
        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(3f)) {
            Image(
                painter = rememberImagePainter(data = image),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}