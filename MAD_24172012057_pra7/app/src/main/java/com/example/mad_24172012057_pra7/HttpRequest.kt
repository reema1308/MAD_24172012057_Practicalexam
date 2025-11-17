package com.example.mad_24172012057_pra7

import android.util.Log
import android.util.MalformedJsonException
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.ProtocolException
import java.net.URL

class HttpRequest {
    private val TAG = "HttpRequest"
    fun makeServiceCall(url: String?,token: String?=null): String? {
        var response: String? = null
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            if (token != null) {
                connection.setRequestProperty("Authorization", "Bearer $token")
                connection.setRequestProperty("content-type", "application/json")
            }
            connection.requestMethod = "GET"
            response = convertStreamToString(BufferedInputStream(connection.inputStream))
        }catch (e: MalformedJsonException){
            Log.e(TAG, "MalformedJsonException: ${e.message}")
        }catch (e: ProtocolException){
            Log.e(TAG, "ProtocolException: ${e.message}")
        }catch (e: IOException){
            Log.e(TAG, "IOException: ${e.message}")
        }catch (e: Exception){
            Log.e(TAG, "Exception: - ${e.message}")
        }
        return response

    }
    private fun convertStreamToString(`is`: InputStream): String{
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append('\n')
            }
        }catch (e: IOException){
            Log.e(TAG, "convertStreamToString IO Exception: ${e.message}")
        }finally {
            try {
                `is`.close()
            }catch (e: IOException){
                e.printStackTrace()
            }
        }

        return sb.toString()

    }

}