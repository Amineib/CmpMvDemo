package com.naar.nmovies.presentation.screens.serielist


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.collect
import com.naar.nmovies.presentation.screens.serielist.components.SerieListItem
import com.naar.nmovies.utils.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SerieListScreen(
    viewModel: SerieListViewModel = hiltViewModel(),
    onItemClicked : (Int) -> Unit
) {

    viewModel.context = LocalContext.current
    //viewModel.createInterstitial()


    Scaffold(

    ) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            val series = viewModel.series.collectAsLazyPagingItems()

            LaunchedEffect(key1 = true){
                viewModel.uiEvent.collect {  event ->
                    when(event)
                    {
                        is SerieListEvent.OnItemClicked -> {
                            onItemClicked(event.serieId)
                        }
                    }
                }
            }


            val state = rememberLazyListState()
            LazyVerticalGrid(cells = GridCells.Fixed(2), contentPadding = PaddingValues(12.dp), state = state){



                items(series) { serie ->
                    serie?.let {
                        SerieListItem(
                            serie = serie,
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                                .padding(4.dp),
                            onItemClicked = {
                                viewModel.onEvent(SerieListEvent.OnItemClicked(serie.id))
                            }
                        )
                    }
                }
            }
        }
    }

}



//TODO : should move to extentions
@ExperimentalFoundationApi
fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}




@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text, onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused
                }
        )

        if (!isHintDisplayed) {
            if (text == "") {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }
        }
    }
}
