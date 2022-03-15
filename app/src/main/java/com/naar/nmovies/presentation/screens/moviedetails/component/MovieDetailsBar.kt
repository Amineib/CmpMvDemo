package com.naar.nmovies.presentation.screens.moviedetails.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.naar.nmovies.domain.models.Genre


@Composable
fun MovieDetailsBar(
    modifier : Modifier = Modifier,
    image : String,
    title: String,
    note : String,
    genres : List<Genre> = emptyList(),
    synopsys: String
    
) {
    Surface(
        elevation = 5.dp
    ) {
        Column (modifier = modifier){
            ItemCard(image = image, title = title, note = note, modifier = Modifier.height(200.dp), genres = genres)
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)).shadow(1.dp)
                    .padding(8.dp)
            ) {
                Text(text = synopsys, style = MaterialTheme.typography.body1, textAlign = TextAlign.Justify)
            }
        }
    }
}

@Composable
fun HeaderTitle(
    modifier : Modifier = Modifier,
    title: String
) {
    Text(text = title,
        style = MaterialTheme.typography.h6,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        color = MaterialTheme.colors.secondary
    )
}

@Composable
fun Note(
    modifier: Modifier = Modifier,
    note: String
) {
    Row(modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start) {
        Text(
            text = note,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    note: String,
    genres: List<Genre>
) {
    Row (
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp)
                .weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderTitle(title = title, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(6.dp))
            Note(note = note)
            Spacer(modifier = Modifier.height(6.dp))
            GenresGrid(genres = genres, modifier = Modifier
                .fillMaxWidth())
        }

        Column (modifier = Modifier.weight(3f)){
            Image(
                painter = rememberImagePainter(data = image),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenresGrid(
    modifier: Modifier = Modifier,
    genres: List<Genre>
) {
    var state = rememberScrollState()
    Row(
        modifier = modifier
            .horizontalScroll(state)
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for(genre in genres){
            Column(modifier = Modifier.padding(start = 4.dp, end = 4.dp).clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colors.primary)) {
                Text(
                    text =  genre.name ?: "",
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

