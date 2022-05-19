package com.example.flowrspot.utility.exception

import java.io.IOException

class InternetConnectivityException : IOException() {

    override fun getLocalizedMessage(): String {
        return "No Internet Connection"
    }
}