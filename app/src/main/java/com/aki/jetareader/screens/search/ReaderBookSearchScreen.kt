package com.aki.jetareader.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aki.jetareader.components.InputField
import com.aki.jetareader.components.ReaderAppBar
import com.aki.jetareader.model.Item
import com.aki.jetareader.model.MBook
import com.aki.jetareader.navigation.ReaderScreens
import java.util.stream.IntStream.range

@Composable
fun SearchScreen(navController: NavController,viewModel: BookSearchViewModel =hiltViewModel()){
    Scaffold (topBar = {
        ReaderAppBar(title = "Search", navController =navController, icon = Icons.Default.ArrowBack, showProfile = false ){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }){
        Surface(modifier = Modifier.padding(it)) {
            Column {
                SearchForm(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){ searchQuery->
                    viewModel.searchBooks(query =searchQuery )
                }
            }
            Spacer(modifier = Modifier.height(13.dp))
            BookList(navController = navController)
        }
    }
}

@Composable
fun SearchForm(
    modifier: Modifier=Modifier,
    loading:Boolean=false,
    hint:String="Search",
    onSearch:(String)-> Unit={}
){
    val keyboardController= LocalSoftwareKeyboardController.current
    Column {
        val searchQueryState= rememberSaveable{mutableStateOf("")}
        val valid= remember (searchQueryState){
            searchQueryState.value.trim().isNotEmpty()
        }
        
        InputField(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            keyboardActions = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}
@Composable
fun BookList(navController: NavController,viewModel: BookSearchViewModel= hiltViewModel()){
    val listofBooks=viewModel.list
//    val listofBooks= listOf(
//        MBook(id ="dada",title="hrhhrl", author = "ldkjdgfg", notes = "kjdfghd"),
//        MBook(id ="dda",title="hrhh", author = "ldkjdg", notes = "kjdfghd"),
//        MBook(id ="dad",title="hhrl", author = "ldkjdgg", notes = "kjdfghd"),
//        MBook(id ="daa",title="hrhl", author = "ldkjfg", notes = "kjdfghd")
//    )
    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)){
        items(items = listofBooks ){book->
            BookCard(book,navController)
        }
    }
}


@Composable
fun BookCard(book:Item,navController: NavController,onClick:() ->Unit={}){
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier= Modifier
            .clickable { onClick.invoke() }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
    ) {
        Row(modifier=Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start) {
            val imageUrl="http://books.google.com/books/content?id=94xjDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            Image(painter = rememberAsyncImagePainter(model = "https://books.google.com/books/content?id=94xjDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"), contentDescription ="book image" )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text ="Id: ${book.volumeInfo.infoLink}", style =MaterialTheme.typography.titleLarge, overflow = TextOverflow.Ellipsis)
                Text(text = "Title:${book.volumeInfo.title}", style =MaterialTheme.typography.titleMedium, overflow = TextOverflow.Ellipsis)
                Text(text ="Authors:${book.volumeInfo.authors}", style =MaterialTheme.typography.titleMedium, overflow = TextOverflow.Clip)
                Text(text = "Notes:${book.volumeInfo.description}", style =MaterialTheme.typography.titleSmall, overflow = TextOverflow.Clip)

            }
        }
    }
}