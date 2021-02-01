package com.example.decodingexercise.helper

import android.content.Context

object ExternalStorageHelper {

    fun getExternalStorage(context: Context): String {
        val externalStorage = context.getExternalFilesDir("audioData")
        return externalStorage?.absolutePath.toString()
    }
}