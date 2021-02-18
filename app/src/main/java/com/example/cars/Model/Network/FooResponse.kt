package com.example.news_app.model.Generic


class FooResponse<T>(
    val status: String?,

    val code: String?,
    val locale: String?,
    val message: String?,

    val data: T? = null
) {

}