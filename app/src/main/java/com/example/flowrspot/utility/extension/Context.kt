package com.example.flowrspot.utility.extension

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.flowrspot.R

/**
 * Displays a short toast using CharSequence as e message.
 */
fun Context.shortToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

/**
 * Displays a short toast using @StringRes as e message.
 */
fun Context.shortToast(@StringRes resId: Int) =
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

/**
 * Displays a long toast using CharSequence as e message.
 */
fun Context.longToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

/**
 * Displays a long toast using @StringRes as e message.
 */
fun Context.longToast(@StringRes resId: Int) =
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

/**
 * Provides a progressDialog.
 */
fun Context.progressDialog(): Dialog {
    val dialog = Dialog(this)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.custom_progress_dialog)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}

/**
 * Check for internet connection.
 */
fun Context.isConnectedToInternet() : Boolean {
    val connectivityManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

    if (network == null)
        return false

    if (networkCapabilities == null)
        return false

    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        return true

    return false
}

