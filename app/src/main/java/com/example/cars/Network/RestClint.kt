package com.example.cars.Network

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams

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

    fun post(
        url: String,
        params: RequestParams?,
        responseHandler: AsyncHttpResponseHandler?
    ) {
        client.addHeader("x-rapidapi-key","a8cbb70bdfmsh8782ff312a34aafp1d9d00jsn7018202ce634")
        client.post(url, params, responseHandler)
    }



}