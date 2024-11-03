package com.aki.jetareader.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aki.jetareader.data.Resource
import com.aki.jetareader.model.Item
import com.aki.jetareader.repository.BookRepositotry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepositotry):ViewModel() {
    var list:List<Item> by mutableStateOf(listOf())

    init{
        loadBooks()
    }
    private fun loadBooks(){
        searchBooks("android")
    }
   fun searchBooks(query: String){
        viewModelScope.launch ( Dispatchers.Default ){
            if (query.isEmpty()){
                return@launch
            }
            try {
                when(val response=repository.getBooks(query)){
                    is Resource.Success -> {
                        list= response.data!!
                    }
                    is Resource.Error ->{
                        Log.e("Netwotk","searchBooks:failed getting books")
                    }
                    else ->{}
                }

            }catch (exception:Exception) {

                Log.d("Network", "searchBooks:${exception.message.toString()}")
            }
        }
    }
}