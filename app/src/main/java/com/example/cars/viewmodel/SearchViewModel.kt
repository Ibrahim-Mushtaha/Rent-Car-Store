package com.example.cars.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cars.Model.Guarantees
import com.example.cars.Network.RestClint
import com.example.news_app.model.Generic.FooWrapper
import com.google.gson.reflect.TypeToken
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class SearchViewModel(application: Application):AndroidViewModel(application) {

    val generator = FooWrapper()
    var restClint = RestClint()

    private val _guarantees = MutableLiveData<List<Guarantees>>()

    val carsGuarantees: LiveData<List<Guarantees>>
        get() = _guarantees


    private val _roadSide = MutableLiveData<List<Guarantees>>()

    val carsRoadSide: LiveData<List<Guarantees>>
        get() = _roadSide



     fun getResponse(responseType:String,carID:Long){
        restClint.get("https://stg.dcetest.com/api/$responseType/"+carID,object :
            JsonHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: JSONObject?
            ) {
                if (statusCode == 200) {
                    val data = responseString!!.getJSONArray("data")
                    val mutableListTutorialType =
                        object : TypeToken<MutableList<Guarantees>>() {}.type
                    if (responseType == "guarantees"){

                        val newArray = generator.getResponse(data.toString(), Guarantees::class.java,mutableListTutorialType)

                        _guarantees.value = newArray
                    }else{
                        val newArray = generator.getResponse(data.toString(), Guarantees::class.java,mutableListTutorialType)

                        _roadSide.value = newArray
                    }
                    Log.e("ggg $responseType", responseString.toString())
                }else{
                    Log.e("ggg $responseType", statusCode.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("ggg error $responseType",throwable!!.message.toString())
            }

        })
    }

}