package com.example.cars.Model.Network

import android.util.Log
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class RestClint {
    companion object {
         val BASE_URL =
            "https://stg.dcetest.com/api/cars"
        val Guarantees_URL = "https://stg.dcetest.com/api/guarantees/"
    }
    private val client = AsyncHttpClient()

    fun get(
       url: String,
        responseHandler: AsyncHttpResponseHandler?
    ) {
        client[url, responseHandler]
    }


    fun get_guarantees(
        url: String,
        responseHandler: AsyncHttpResponseHandler?
    ){
        getAbsoluteUrl(url)
    }


    fun post(
        url: String,
        params: RequestParams?,
        responseHandler: AsyncHttpResponseHandler?
    ) {
        client.addHeader("x-rapidapi-key","a8cbb70bdfmsh8782ff312a34aafp1d9d00jsn7018202ce634")
        client.post(url, params, responseHandler)
    }

    private fun getAbsoluteUrl(relativeUrl: String): String? {
        return Guarantees_URL + relativeUrl
    }



    fun uploudFile(
        url: String,
        params: RequestParams?,
        responseHandler: AsyncHttpResponseHandler?
    ) {
        client.post(getAbsoluteUrl(url), params, responseHandler)
    }


}