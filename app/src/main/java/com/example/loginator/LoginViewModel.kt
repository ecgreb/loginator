package com.example.loginator

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
}
