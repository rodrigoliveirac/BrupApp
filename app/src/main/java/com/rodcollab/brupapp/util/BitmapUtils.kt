package com.rodcollab.brupapp.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream

internal suspend fun createScreenshot(view: View, ctx: Context): Uri {
    var outputStream: OutputStream?
    var img: Uri?
    val bm = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bm)
    view.draw(canvas)

    val contentValues = ContentValues().apply {
        withContext(Dispatchers.IO) {
            put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString())
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }
    }
    val contentResolver = ctx.applicationContext.contentResolver
    contentResolver.also { resolver ->
        withContext(Dispatchers.IO) {
            img = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            outputStream = img?.let {
                resolver.openOutputStream(it)
            }

            outputStream?.use { bm.compress(Bitmap.CompressFormat.PNG, 70, it) }

            contentValues.clear()
            contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
            img?.let { resolver.update(it, contentValues, null, null) }
        }
    }
    return img!!
}
