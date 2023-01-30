package com.manish.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher


object IntentUtils {

    fun openCamera(resultLauncher: ActivityResultLauncher<Intent>) {
        resultLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    fun openSetting(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

}