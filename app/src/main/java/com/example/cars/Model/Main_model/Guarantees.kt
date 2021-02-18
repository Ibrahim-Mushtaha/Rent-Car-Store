package com.example.cars.Model.Main_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Guarantees(
    val id: Long,
    val title: String,
    val description: String,
    val status: String,
    val status_value: String,
    val slug: String,
    val price: String,
    val years: String,
    val created_at: String
):Parcelable