package com.busy.honey.stock.investment.response

data class RestApiResponse(
    val status: String,
    val description: String,
    val data: MutableMap<Any, Any>?
)
