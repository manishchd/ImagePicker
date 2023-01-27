package com.manish.interfaces

import java.io.File

interface IMediaResult {

    fun onSuccess(file: File)
    fun onError(error: String, exception: Exception?){}

}