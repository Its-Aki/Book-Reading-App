package com.aki.jetareader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aki.jetareader.R
import com.aki.jetareader.components.EmailInput
import com.aki.jetareader.components.PasswordInput
import com.aki.jetareader.components.ReaderLogo
import com.aki.jetareader.components.SubmitButton
import com.aki.jetareader.navigation.ReaderScreens

@Composable
fun LoginScreen(navController: NavController,viewModel: LoginScreenViewModel= androidx.lifecycle.viewmodel.compose.viewModel()){
    val showLoginForm= rememberSaveable {
        mutableStateOf(true)
    }

    Surface (modifier = Modifier
        .fillMaxSize()
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            if (showLoginForm.value)
                UserForm(loading = false,isCreatedAccount = false){email,pwd->
                    viewModel.signInWithEmailPassword(email=email, password =pwd ){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
            }
            else
                UserForm(loading = false,isCreatedAccount = true){email,pwd->
                    viewModel.createUserWithEmailAndPassword(email = email, password = pwd){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val text=if(showLoginForm.value) "Sign Up" else "Login"
            val newOrUser=if(showLoginForm.value) "New user ?" else "Already a user ?"
            Text(text = newOrUser)
            Text(text = text,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color =MaterialTheme.colorScheme.secondaryContainer
                )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading:Boolean=false,
    isCreatedAccount:Boolean=false,
    onDone:(String,String)->Unit={email,pwd->

    }
){
    val keyboardController=LocalSoftwareKeyboardController.current
    val email= rememberSaveable { mutableStateOf("") }
    val password= rememberSaveable { mutableStateOf("") }
    val passwordVisibility= rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest= FocusRequester.Default
    val valid= remember(email.value,password.value){
        email.value.trim().isNotEmpty()&&password.value.trim().isNotEmpty()
    }
    val modifier= Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreatedAccount) Text(text = stringResource(id = R.string.create_acct))
        else Text(text = "")
        EmailInput(emailState = email, enabled =!loading, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })
        PasswordInput(
            modifier=Modifier.focusRequester(passwordFocusRequest),
            passwordState=password,
            labelId="password",
            enabled=!loading,
            passwordVisibility=passwordVisibility,
            onAction= KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(),password.value.trim())
            }
        )
        SubmitButton(
            textId = if(isCreatedAccount) "Create Account" else "Login",
            loading=loading,
            validInputs=valid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

