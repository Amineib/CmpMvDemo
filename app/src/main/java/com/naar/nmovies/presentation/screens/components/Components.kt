package com.naar.nmovies.presentation.screens.moviedetails.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naar.nmovies.presentation.theme.Roboto

@Composable
fun SectionTitle(
    modifier : Modifier = Modifier,
    title: String = "",
    titleColor: Color = Color.Black
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        TextWithShadow(text = title, modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun TextWithShadow(
    text: String,
    modifier: Modifier,
    titleColor: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Center,
    textStyle: TextStyle = MaterialTheme.typography.h6
) {
    Text(
        text = text,
        color = Color.DarkGray,
        modifier = modifier
            .offset(
                x = 1.dp,
                y = 1.dp
            )
            .alpha(0.2f),
        textAlign = textAlign,
        style = textStyle,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    Text(
        textAlign = textAlign,
        text = text,
        color = titleColor,
        modifier = modifier,
        style = textStyle,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesGrid(
    modifier: Modifier = Modifier,
    categories: List<String>
) {
    var state = rememberScrollState()
    Row(
        modifier = modifier
            .horizontalScroll(state)
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for(category in categories){
            Column(modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.primary)) {
                Text(
                    text =  category,
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

@Composable
fun DescriptionText(
    modifier : Modifier = Modifier,
    text: String
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .shadow(1.dp)
        .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, style = MaterialTheme.typography.body1, textAlign = TextAlign.Justify)
    }
}

@Composable
fun HomeHeaderTitle(
    modifier:  Modifier = Modifier,
    text: String
) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
        Text(text = text,
            color = MaterialTheme.colors.onBackground,
            fontFamily = Roboto,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            )
    }
}



@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
    text: String
) {
    Row(modifier = modifier,
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .padding(2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                //Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 15.sp,
                    letterSpacing = 4.sp
                )
                Icon(Icons.Filled.PlayArrow, contentDescription = "play")
            }
        }
    }

}
