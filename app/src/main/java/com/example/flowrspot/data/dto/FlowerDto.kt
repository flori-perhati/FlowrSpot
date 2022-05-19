package com.example.flowrspot.data.dto

import com.example.flowrspot.domain.model.FlowerModel

data class FlowerDto(
    val flowers: List<Flower>,
    val meta: Meta
)

fun FlowerDto.toFlowerModelList(): List<FlowerModel> {
    return flowers.map {
        it.toFlowerModel()
    }
}