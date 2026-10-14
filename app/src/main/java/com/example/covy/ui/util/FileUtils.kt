package com.example.covy.ui.util


import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

// Retrouve le nom d'un fichier choisi par l'utilisateur
fun Context.displayName(uri: Uri): String? =
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (index >= 0 && cursor.moveToFirst()) cursor.getString(index) else null
    }