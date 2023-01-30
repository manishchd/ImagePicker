package com.manish.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.OutputStream


object ImageUtils {

    private fun saveBitmapToCache(context: Context, bitmap: Bitmap) {
        val filename = "final_image${System.currentTimeMillis()}}.jpg"
        val cacheFile = File(context.cacheDir, filename)
        val out: OutputStream = cacheFile.outputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
    }

    fun checkPermissionGivenInManifest(context: Context) {
        val pm: PackageManager = context.packageManager
        try {
            val packageInfo: PackageInfo =
                pm.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            val requestedPermissions: Array<String> = packageInfo.requestedPermissions
            requestedPermissions.forEach { permission ->
                Log.i("PERMISSIONS", "" + permission )
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

}