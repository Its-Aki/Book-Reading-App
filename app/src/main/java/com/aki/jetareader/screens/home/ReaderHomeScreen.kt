package com.aki.jetareader.screens.home

import android.widget.HorizontalScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.aki.jetareader.components.BookRating
import com.aki.jetareader.components.FABContent
import com.aki.jetareader.components.ListCard
import com.aki.jetareader.components.ReaderAppBar
import com.aki.jetareader.components.RoundedButton
import com.aki.jetareader.components.TitleSection
import com.aki.jetareader.model.MBook
import com.aki.jetareader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController){
Scaffold(
    topBar = {
             ReaderAppBar(title = "A.Reader", navController =navController )
    },
    floatingActionButton = {
        FABContent{
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    },

) { it->
    Surface(modifier = Modifier
        .padding(it)
    ) {
        HomeContent(navController = navController)
    }
}
}
@Composable
fun HomeContent(navController: NavController) {
    val listofBooks= listOf(
        MBook(id ="dada",title="hrhhrl", author = "ldkjdgfg", notes = "kjdfghd"),
        MBook(id ="dda",title="hrhh", author = "ldkjdg", notes = "kjdfghd"),
        MBook(id ="dad",title="hhrl", author = "ldkjdgg", notes = "kjdfghd"),
        MBook(id ="daa",title="hrhl", author = "ldkjfg", notes = "kjdfghd")
    )

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (email.isNullOrEmpty())
        email?.split("@")?.get(0)
    else {
        "N/A"
    }

    Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n" + "activity right now..")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile icon",
                    modifier = Modifier
                        .clickable { }
                        .size(45.dp),
                    tint = Color.Transparent)
                if (currentUserName != null) {
                    Text(
                        text = currentUserName,
                        modifier = Modifier.padding(2.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Red,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                    Divider()
                }
            }
        }
        ReadingRightNowArea(books = listOf(),navController=navController )
        TitleSection(label = "Reading List")

        BookListArea(listOfBooks=listofBooks,navController=navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
   HorizontalScrollableComponent(listOfBooks){
       //todo on click card details
   }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>,onCardPress:(String)->Unit) {
val scrollState= rememberScrollState ()
    Row (modifier = Modifier
        .fillMaxWidth()
        .heightIn(200.dp)
        .horizontalScroll(state = scrollState)){
        for (book in listOfBooks){
            ListCard(book){
                onCardPress(it)
            }
        }
    }
}

@Composable
fun ReadingRightNowArea(books:List<MBook>,navController: NavController){
    ListCard()
}

