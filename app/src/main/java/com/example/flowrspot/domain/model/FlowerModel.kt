package com.example.flowrspot.domain.model

data class FlowerModel(
    val name: String,
    val latinName: String,
    val profilePicture: String,
    val favorite: Boolean,
    val sightings: Int
) {
    val imagePath: String
        get() = "https:$profilePicture"

    val numberOfSightings: String
        get() {
            return if (sightings == 1) {
                "$sightings sighting"
            } else {
                "$sightings sightings"
            }
        }
}
