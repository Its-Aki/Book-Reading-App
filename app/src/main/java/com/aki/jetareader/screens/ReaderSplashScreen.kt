package com.aki.jetareader.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aki.jetareader.components.ReaderLogo
import com.aki.jetareader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun ReaderSplashScreen(navController: NavController) {
    val scale= remember{
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(0.9f, tween(800, easing ={
            OvershootInterpolator(8f)
                .getInterpolation(it)
        }))
        delay(2000L)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(ReaderScreens.LoginScreen.name)
        }else{
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }
   Surface(modifier = Modifier
       .padding(15.dp)
       .size(335.dp)
       .scale(scale.value),
       shape = CircleShape,
       border = BorderStroke(5.dp, Color.LightGray)
   ) {
       Column (modifier = Modifier
           .padding(1.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ){
           ReaderLogo()
           Spacer(modifier = Modifier.height(45.dp))
           Text(
               text = "\"Read Change YourSelf\"",
               fontStyle =FontStyle.Italic,
               style = MaterialTheme.typography.titleMedium
           )
       }
   }
}