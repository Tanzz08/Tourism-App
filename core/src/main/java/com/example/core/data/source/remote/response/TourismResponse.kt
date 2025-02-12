package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TourismResponse(

	@field:SerializedName("places")
	val places: List<PlacesItem>,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class PlacesItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("like")
	val like: Int,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("longitude")
	val longitude: Double
)
