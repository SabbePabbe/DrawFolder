package com.example.gestureunlock.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Draw your custom pattern"
    }
    val text: LiveData<String> = _text
}