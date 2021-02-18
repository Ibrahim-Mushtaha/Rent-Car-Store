package com.example.news_app.model.Generic

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class FooWrapper {


    private val gson = Gson()

    fun <T> getResponse(
        jsonArray:String,
        dataClass: Class<T>?,
        type: Type?
    ): ArrayList<T> {
        return gson.fromJson(jsonArray, type)
    }



    private fun getType(
        rawClass: Class<*>,
        parameterClass: Class<*>
    ): Type? {
        return object : ParameterizedType {


            override fun getRawType(): Type {
               return rawClass
            }

            override fun getOwnerType(): Type? {
               return null
            }

            override fun getActualTypeArguments(): Array<Type> {
              return arrayOf(parameterClass)
            }
        }
    }
}