package com.example.internet_test.model.remote

import android.net.Uri
import android.util.Log
import com.example.internet_test.model.BookItem
import com.example.internet_test.model.BooksResponse
import com.example.internet_test.model.VolumeInfo
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

val TAG = "Network"

class Network {

    private val BASE_URL = "https://www.googleapis.com/"
    private val END_POINT = "books/v1/volumes"
    private val PARAM_Q = "q"
    private val PARAM_RESULT = "maxResult"
    private val PARAM_PRINT_TYPE = "printType"

    fun executeNetworkCall(bookQuery: String, maxResult: Int, printType: String):  BooksResponse{
        val uri = Uri.parse(BASE_URL + END_POINT).buildUpon()
            .appendQueryParameter(PARAM_Q, bookQuery)
            .appendQueryParameter(PARAM_RESULT, maxResult.toString())
            .appendQueryParameter(PARAM_PRINT_TYPE, printType).build()
        val url = URL(uri.toString())

        val httpConnection = url.openConnection() as HttpURLConnection
        httpConnection.connectTimeout = 10000
        httpConnection.readTimeout = 15000
        httpConnection.requestMethod = "GET"
        httpConnection.doInput = true

        httpConnection.connect()

        val isResponse = httpConnection.inputStream
        val responseCode = httpConnection.responseCode

        Log.d(TAG, "executeNetworkCall: isResponse= $isResponse")
        Log.d(TAG, "executeNetworkCall: responseCode= $responseCode")

        val stringResponse = parseInputStream(isResponse)

        return formatterStringToBookResponse(stringResponse)
    }

    private fun parseInputStream(response: InputStream): String {
        val result = StringBuilder()
        val reader = BufferedReader(InputStreamReader(response))

        var currentLine = reader.readLine()

        while (currentLine != null) {
            result.append(currentLine)
            currentLine = reader.readLine()
        }

        return if (result.isEmpty()) "" else  result.toString()

    }

    private fun formatterStringToBookResponse(response: String): BooksResponse {

        val jsonObj = JSONObject(response)
        val items = jsonObj.getJSONArray("items")
        var listOfBookItem = mutableListOf<BookItem>()
        for (i in 0 until items.length()) {
            val volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo")
            val title = volumeInfo.getString("title")
            val authors = volumeInfo.getJSONArray("authors")

            var listOfAuthor = mutableListOf<String>()
            for (e in 0 until authors.length()) {
                val author = authors.getString(e)
                listOfAuthor.add(author)
            }

            listOfBookItem.add(BookItem(VolumeInfo(title, listOfAuthor)))
        }
        return BooksResponse(listOfBookItem)
    }
}