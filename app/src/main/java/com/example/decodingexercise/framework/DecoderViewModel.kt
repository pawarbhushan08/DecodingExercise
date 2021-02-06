package com.example.decodingexercise.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.decodingexercise.logic.PythonServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DecoderViewModel(application: Application, val pythonServices: PythonServices) :
    AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val bitArray = MutableLiveData<String>()

    fun showEncoderResult(fileName: String) {
        coroutineScope.launch {
            bitArray.postValue(pythonServices.getFskDemodulation(fileName))
        }
    }
}