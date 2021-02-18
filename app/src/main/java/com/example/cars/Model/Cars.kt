package com.example.cars.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Cars (
    val carNew: List<CarNewElement>,
    val carUsed: List<CarNewElement>
):Parcelable

@Parcelize
data class CarNewElement (
    val id: Long,
    val title: String,
    val brand: Brand,
    val model: Brand,
    val carClass: Brand,
    val city: City,
    val status: Status,
    val status_value: StatusValue,
    val type: String,
    val option_type: String,
    val origin_type: String,
    val transmission: String,
    val fuel_type: String,
    val year: String,
    val main_image: String,
    val images: List<String>,
    val created_at: String,
    val price: String,
    val cylinder_no: String,
    val tax: String,
    val tax_value: String? = null,
    val mileage: String,
    val car_interior: String,
    val registration: String,
    val registration_cost: String,
    val additional_features: String,
    val distributor: Distributor,
    val isMain: Boolean
):Parcelable


data class BodyInspection (
    val x: Long,
    val y: Long,
    val side: String,
    val attachment: String,
    val attachmentURL: String
)

@Parcelize
data class Brand (
    val id: Long,
    val title: String,
    val status: Status,
    val status_value: StatusValue,
    val slug: String? = null,
    val image: String? = null,
    val models_count: Long? = null,
    val created_at: String,
    val years: String? = null,
    val car_model_id: Long? = null,
    val feature_id: Long? = null,
    val car_brand_id: Long? = null
):Parcelable

enum class Status {
    Active
}

enum class StatusValue {
    Active
}

@Parcelize
data class City (
    val id: Long,
    val title: String,
    val shippingPrice: Long
):Parcelable

@Parcelize
data class Distributor (
    val id: Long,
    val name: String,
    val address: String,
    val status: Status,
    val statusValue: StatusValue,
    val slug: String,
    val image: String,
    val email: String,
    val mobile: String,
    val hasCp: Boolean,
    val hasManger: Boolean
):Parcelable

data class Feature (
    val id: Long,
    val title: String,
    val status: Status,
    val status_value: StatusValue,
    val created_at: String,
    val sub_features: List<Brand>
)
