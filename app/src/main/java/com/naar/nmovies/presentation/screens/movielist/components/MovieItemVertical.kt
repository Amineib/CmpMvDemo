package com.naar.nmovies.presentation.screens.movielist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.naar.nmovies.presentation.screens.moviedetails.component.TextWithShadow
import com.naar.nmovies.presentation.screens.uimodels.MovieItemUi
import com.naar.nmovies.utils.Constants
import com.naar.nmovies.R

@Composable
fun MovieItemVertical(
    movie: MovieItemUi,
    modifier : Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Surface(
        elevation = 5.dp,
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .padding(end = 10.dp)
            .shadow(4.dp, RoundedCornerShape(0.dp))
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .width(350.dp)
                .padding(1.dp)
                .clickable { onClick(movie.id) },
            Alignment.BottomStart
        ){
            val imgUrl = Constants.BASE_IMG_URL + movie.backDropPath //replace by backdrop path
            val painter = rememberImagePainter(data = imgUrl, builder = {
                crossfade(1000)
            })

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(5.dp),
                contentScale = ContentScale.Crop,
            )
            TextWithShadow(text = movie.name, modifier = modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
                textAlign = TextAlign.Start,
                titleColor = MaterialTheme.colors.onPrimary,
                textStyle = MaterialTheme.typography.h6
            )

            when(painter.state) {
                is ImagePainter.State.Loading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_hide_image_24),
                            contentDescription = "Loading..",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                is ImagePainter.State.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_error_outline_24),
                            contentDescription = stringResource(id = R.string.loading_message ),
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = movie.name)
                    }
                }
            }
        }
    }

}


@Preview
@Composable
fun PreviewIt() {
    //MovieItemVertical(null,onClick = {})
}