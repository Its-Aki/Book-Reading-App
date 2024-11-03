package com.aki.jetareader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aki.jetareader.model.MUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginScreenViewModel:ViewModel() {
    val loadingState= MutableStateFlow(LoadingState.IDLE)
    private val auth:FirebaseAuth = Firebase.auth

    private val _loading=MutableLiveData(false)
    val loading:LiveData<Boolean> = _loading

    fun signInWithEmailPassword(email:String,password:String,home:()-> Unit)
    =viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        Log.d("fb","signInWithEmailAndPassword yayyyyy: ${task.result.toString()}")
                        home()

                    }else{
                        Log.d("fb","signInWithEmailAndPassword: ${task.result.toString()}")
                    }

                }
        }catch (ex:Exception){
            Log.d("fb","signInWithEmailAndPassword: ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(email: String,password: String,home: () -> Unit){
        if (_loading.value==false){
            _loading.value=true
            try {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task->
                        if (task.isSuccessful){
                            val displayName= task.result.user?.email?.split('@')?.get(0)
                            createUser(displayName)
                            home()
                        }else{
                            Log.d("fb","createUserWithEmailAndPassword:${task.result.toString()}")
                        }
                        _loading.value=false
                    }
            }catch (ex:Exception){
                Log.d("fb","createWithEmailAndPassword:${ex.message}")
            }

        }

    }

    private fun createUser(displayName: String?) {
        val userId= auth.currentUser?.uid
        val user= MUser(
            userId=userId.toString(), displayName = displayName.toString(), avatarUrl = "", quote = "life is great",
            profession = "Android developer",
            id=null
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }
}