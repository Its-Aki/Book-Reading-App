package com.aki.jetareader.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aki.jetareader.model.MBook
import com.aki.jetareader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(modifier=modifier
        .padding(15.dp),
        text = "A. Reader",
        fontStyle = FontStyle.Italic,
        color = Color.Red.copy(0.5f),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun EmailInput(
    modifier: Modifier=Modifier,
    emailState: MutableState<String>,
    labelId:String="Email",
    enabled:Boolean=true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
){
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        imeAction = imeAction,
        keyboardActions = onAction,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun InputField(
    modifier: Modifier=Modifier,
    valueState: MutableState<String>,
    labelId:String="Email",
    enabled:Boolean=true,
    isSingleLine:Boolean=true,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default
){
    OutlinedTextField(value = valueState.value, onValueChange = { valueState.value = it },
        singleLine = isSingleLine, textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ), modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = keyboardActions,
        label = { Text(text = labelId) }
    )

}

@Composable
fun PasswordInput(modifier: Modifier,
                  passwordState: MutableState<String>,
                  enabled: Boolean,
                  labelId:String,
                  passwordVisibility: MutableState<Boolean>,
                  onAction: KeyboardActions= KeyboardActions.Default,
                  imeAction: ImeAction=ImeAction.Done) {
    val visualTransformation=if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange ={ passwordState.value=it },
        label = { Text(text = labelId)},
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier= modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions=onAction,
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordVisibility=passwordVisibility)}
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible=passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value=!visible}) {
        Icons.Default.Close
    }
}

@Composable
fun SubmitButton(
    textId : String,
    loading:Boolean,
    validInputs:Boolean,
    onClick:()->Unit)
{
    Button(onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth() ,
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading)
            CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else
            Text(text = textId,modifier=Modifier.padding(5.dp))

    }
}

@Composable
fun TitleSection(modifier: Modifier=Modifier,label:String){
    Surface (modifier = modifier.padding(start = 5.dp, top = 1.dp)){
        Column {
            Text(text = label, fontSize = 19.sp, fontStyle = FontStyle.Normal, textAlign = TextAlign.Left)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    title:String,
    showProfile: Boolean=true,
    icon:ImageVector?=null,
    navController: NavController,
    onBackArrowClicked:()->Unit={}
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "Logo",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .scale(0.8f))
                if(icon!=null){
                    Icon(imageVector = icon, contentDescription ="arrow back" , tint = Color.Red.copy(0.7f), modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                }
                Spacer(modifier = Modifier.width(40.dp))
                Text(text = title,
                    color = Color.Red.copy(0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20 .sp)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
//                    navController.navigate(ReaderScreens.LoginScreen.name)
                    navController.popBackStack()
                }
            }) {
                if (showProfile) Row {
                    Icon(imageVector = Icons.Default.Close, contentDescription ="LogOut" )
                }else
                    Box{}

            } },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        windowInsets = WindowInsets.navigationBars
    )
}

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        containerColor = Color.Cyan)
    {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Books", tint = Color.White)
    }
}
@Preview
@Composable
fun RoundedButton(
    label:String="Reading",
    radius:Int=29,
    onPress:()->Unit={}
){
    Surface(modifier=Modifier.clip(RoundedCornerShape(
        bottomEndPercent =radius,
        topStartPercent = radius)),
        color = Color.Red
    ) {
        Column (modifier= Modifier
            .width(90.dp)
            .heightIn(40.dp)
            .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            Text(text = label, style = TextStyle(color = Color.White, fontSize = 15.sp))
        }
    }

}

@Composable
fun BookRating(score: Double=4.5) {
    Surface(modifier = Modifier
        .height(70.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 6.dp,
        color =Color.White
    ) {
        Column(modifier=Modifier.padding(4.dp)) {
            Icon(imageVector = Icons.Filled.Star, contentDescription ="star")
            Text(text = score.toString(), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun ListCard(book: MBook = MBook("asd","Running","me and YOu","hello worker"), onPressDetails : (String)-> Unit = {}
){
    val context= LocalContext.current
    val resources=context.resources
    val displayMetrics=resources.displayMetrics

    val screenWidth=displayMetrics.widthPixels / displayMetrics.density
    val spacing=10.dp
    Card (
        shape = RoundedCornerShape(29.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) }
    ){
        Column(modifier=Modifier.width(screenWidth.dp-(spacing*2)),
            horizontalAlignment = Alignment.Start) {

            Row(horizontalArrangement = Arrangement.Center) {
                Image(painter = rememberAsyncImagePainter("https://books.google.com/books/content?id=94xjDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"), contentDescription ="book image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(50.dp))
                Column (modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(imageVector = Icons.Rounded.FavoriteBorder
                        , contentDescription ="fav icon",
                        modifier=Modifier.padding(bottom = 1.dp)
                    )
                    BookRating(score=3.5)
                }

            }
            Text(text =book.title.toString(), modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(text = book.author.toString(), modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodySmall
            )

        }
        Row(modifier=Modifier.fillMaxWidth(1f),verticalAlignment =Alignment.CenterVertically, horizontalArrangement =Arrangement.End)
        {
            RoundedButton(label = "Reading", radius = 60)

        }
    }
}
