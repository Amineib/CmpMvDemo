package com.naar.nmovies.presentation.screens.moviedetails.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.naar.nmovies.R

@Composable
fun CrewProfile(
    modifier: Modifier = Modifier,
    @SuppressLint("ResourceType") imgData : String = stringResource(id = R.drawable.ic_outline_error_outline_24),
    name: String = "Character name"
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircleImage(data = imgData,
        modifier = Modifier.weight(2f))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name,
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun CircleImage(modifier : Modifier = Modifier,
                data : String =  ""
                ) {
    Image(
        painter = rememberImagePainter(
            data = data,
            builder = {
                error(
                    R.drawable.ic_outline_hide_image_24
                )
            }
        ), contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .border(width = 1.dp, shape = CircleShape, color = Color.Black)
    )
}

@Preview
@Composable
fun PrevImage() {
    CircleImage(
    )
}