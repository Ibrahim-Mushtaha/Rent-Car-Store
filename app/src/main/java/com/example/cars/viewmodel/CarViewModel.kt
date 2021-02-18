package com.example.cars.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cars.Model.Brand
import com.example.cars.Model.CarNewElement
import com.example.cars.Network.RestClint.Companion.BASE_URL
import com.example.cars.other.*
import com.example.news_app.model.Generic.FooWrapper
import com.google.gson.reflect.TypeToken
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class CarViewModel(application: Application) : AndroidViewModel(application) {


    val generator = FooWrapper()
    var restClint = com.example.cars.Network.RestClint()

    private val _cars = MutableLiveData<List<CarNewElement>>()

    val carsNewElement: LiveData<List<CarNewElement>>
        get() = _cars


    private val _oldcars = MutableLiveData<List<CarNewElement>>()

    val carsOldElement: LiveData<List<CarNewElement>>
        get() = _oldcars



    init {
        getcarData()
    }

    fun getcarData() {
        restClint.get(BASE_URL, object : JsonHttpResponseHandler() {

            override fun onStart() {
                Log.e("eee start", "onStart")
                super.onStart()
            }

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                if (statusCode == 200) {
                    val x = response!!.getJSONObject(DATA)



                    val car_new=x.getJSONArray("car_new")
                    val car_used=x.getJSONArray("car_used")


                    val mutableListTutorialType =
                        object : TypeToken<MutableList<CarNewElement>>() {}.type




                    val newArray = generator.getResponse(car_new.toString(), CarNewElement::class.java,mutableListTutorialType)

                    _cars.value = newArray
                    val usedArray = generator.getResponse(car_used.toString(), CarNewElement::class.java,mutableListTutorialType)


                    _oldcars.value = usedArray
                    
                    newArray.forEach {
                     Log.e("new array",it.toString())
                    }



                    usedArray.forEach {
                        Log.e("old array",it.toString())
                    }

                } else {
                    Log.e("eee response", statusCode.toString())
                }


            }


            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("eee error", throwable!!.message.toString())
            }
        })

    }


    fun getModelData(brand:String,onComplete:(array:ArrayList<Brand>)->Unit) {
        restClint.get(BASE_URL, object : JsonHttpResponseHandler() {

            override fun onStart() {
                Log.e("eee start", "onStart")
                super.onStart()
            }

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                val arrayList= ArrayList<Brand>()
                if (statusCode == 200) {
                    val x = response!!.getJSONObject(DATA)



                    val car_new=x.getJSONArray(CARNEW)
                    val car_used=x.getJSONArray(CARUSED)


                    val mutableListTutorialType =
                        object : TypeToken<MutableList<CarNewElement>>() {}.type




                    val newArray = generator.getResponse(car_new.toString(), CarNewElement::class.java,mutableListTutorialType)
                    val usedArray = generator.getResponse(car_used.toString(), CarNewElement::class.java,mutableListTutorialType)


                    for (i in 0 until  newArray.size){
                        Log.e("ttt add",newArray[i].brand.title)
                        Log.e("ttt model",newArray[i].model.title)

                        if (newArray[i].brand.title == brand){
                            arrayList.add(newArray[i].model)
                            Log.e("ttt",newArray[i].model.title)
                        }
                        for (y in 0 until usedArray.size){
                            Log.e("ttt full",usedArray[y].model.title)
                            if (usedArray[y].brand.title == brand){
                                Log.e("ttt add",newArray[i].model.title)
                                arrayList.add(usedArray[y].model)
                            }
                        }
                    }
                    onComplete(arrayList)
                } else {
                    Log.e("eee response", statusCode.toString())
                }


            }


            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("eee error", throwable!!.message.toString())
            }
        })

    }


    fun post(url:String,parms:RequestParams){
        restClint.post(url,parms,object :TextHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: String?
            ) {
                Log.e("eee done",response.toString())
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("eee error",throwable.toString())
            }
        })
    }


    fun getselectedModel(brand:String,model_type:String,type:String,onComplete:(array:ArrayList<CarNewElement>)->Unit) {
        restClint.get(BASE_URL, object : JsonHttpResponseHandler() {

            override fun onStart() {
                Log.e("eee start", "onStart")
                super.onStart()
            }

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                var arrayList= ArrayList<CarNewElement>()
                if (statusCode == 200) {
                    val x = response!!.getJSONObject("data")


                    val car_new = x.getJSONArray("car_new")
                    val car_used = x.getJSONArray("car_used")


                    val mutableListTutorialType =
                        object : TypeToken<MutableList<CarNewElement>>() {}.type


                    if (type == "new") {
                        val newArray = generator.getResponse(
                            car_new.toString(),
                            CarNewElement::class.java,
                            mutableListTutorialType
                        )

                        for (i in 0 until newArray.size){
                            Log.e("eee array brand",newArray[i].brand.title)
                            Log.e("eee array model", newArray[i].model.title)
                            if (newArray[i].brand.title == brand  || newArray[i].brand.title == model_type){
                                arrayList.add(newArray[i])
                            }
                        }
                        onComplete(arrayList)
                    }else{
                        val usedArray = generator.getResponse(
                            car_used.toString(),
                            CarNewElement::class.java,
                            mutableListTutorialType
                        )

                        for (i in 0 until usedArray.size){
                            Log.e("eee array brand",usedArray[i].brand.title)
                            Log.e("eee array model", usedArray[i].model.title)
                            if (usedArray[i].brand.title == brand  || usedArray[i].model.title == model_type){
                                arrayList.add(usedArray[i])
                            }
                        }

                        onComplete(arrayList)
                    }
                }


            }


            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("eee error", throwable!!.message.toString())
            }
        })

    }




}