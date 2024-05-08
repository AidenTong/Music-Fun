package com.example.musicfun.ui.MV

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MVViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is MV Fragment"
    }
    val text: LiveData<String> = _text
}