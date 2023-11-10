package com.rodcollab.brupapp.util

import android.content.Context
import android.content.Intent
import android.net.Uri

internal fun Context.sharePerformance(uri: Uri) {
    val share = Intent(Intent.ACTION_SEND)
    share.type = "image/jpeg"
    share.putExtra(Intent.EXTRA_STREAM, uri)
    val appLink = "https://play.google.com/store/apps/details?id=com.rodcollab.brupapp"
    share.putExtra(
        Intent.EXTRA_TEXT,
        "Hey, take a look at the performance I achieved in the Hangman game on the Brup app. I bet you can do better than me! $appLink"
    )
    startActivity(Intent.createChooser(share, "Select"))
}