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

    val bitArray = MutableLiveData<List<Int>>()

    fun launchEncoder(fileName: String) {
        coroutineScope.launch {
            bitArray.postValue(getPythonHelloWorld(fileName))
        }
    }

    private fun getPythonHelloWorld(fileName: String): List<Int> {
        val python = Python.getInstance()
        val pythonFile = python.getModule("fskDecoderScript")

        val audioDirPath = getExternalStorage(this.getApplication())

        val bitStreanArray = pythonFile.callAttr("fsk_demodulator", audioDirPath + '/' + fileName)
        val bitArray = bitStreanArray.toString().replace("[", "").replace("\n", "").replace(" ", "")
            .replace("]", "").split(",").map { it.toInt() }
        return bitArray
    }
}