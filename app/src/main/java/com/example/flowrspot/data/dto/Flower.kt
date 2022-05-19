package com.example.flowrspot.data.dto

import com.example.flowrspot.domain.model.FlowerModel
import com.google.gson.annotations.SerializedName

data class Flower(
    val id: Int,
    val name: String,
    @SerializedName("latin_name")
    val latinName: String,
    val sightings: Int,
    @SerializedName("profile_picture")
    val profilePicture: String,
    val favorite: Boolean
)

fun Flower.toFlowerModel(): FlowerModel {
    return FlowerModel(name, latinName, profilePicture, favorite, sightings)
}