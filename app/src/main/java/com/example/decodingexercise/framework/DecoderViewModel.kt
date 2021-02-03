package com.example.decodingexercise.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chaquo.python.Python
import com.example.decodingexercise.helper.ExternalStorageHelper.getExternalStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DecoderViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val bitArray = MutableLiveData<String>()

    fun launchEncoder(fileName: String) {
        coroutineScope.launch {
            bitArray.postValue(getPythonHelloWorld(fileName))
        }
    }

    private fun getPythonHelloWorld(fileName: String): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("fskDecoderScript")

        val audioDirPath = getExternalStorage(this.getApplication())

        val hexArray = pythonFile.callAttr("fsk_demodulator", audioDirPath + '/' + fileName)
        return hexArray.toString()
    }
}