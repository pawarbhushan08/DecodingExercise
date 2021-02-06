package com.example.decodingexercise.logic

import android.content.Context
import com.chaquo.python.Python
import com.example.decodingexercise.helper.ExternalStorageHelper

class PythonServices(val context: Context) {

    fun getFskDemodulation(fileName: String): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("fskDecoderScript")

        val audioDirPath = ExternalStorageHelper.getExternalStorage(context = context)

        val hexArray = pythonFile.callAttr("fsk_demodulator", audioDirPath + '/' + fileName)
        return hexArray.toString()
    }
}