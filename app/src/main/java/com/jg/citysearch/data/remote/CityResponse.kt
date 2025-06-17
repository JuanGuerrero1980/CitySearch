package com.jg.citysearch.data.remote

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("_id") val id: Int,
    @SerializedName("coord") val coordinates: Coordinate,
    val country: String,
    val name: String
)