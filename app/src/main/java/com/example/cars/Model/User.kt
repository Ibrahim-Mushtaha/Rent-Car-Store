package com.example.cars.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User (var name:String,var email:String,var phone:String,var city: String,var guarantees:String,var road_assistance:String):Parcelable {
    constructor() : this("", "", "","","","")
}