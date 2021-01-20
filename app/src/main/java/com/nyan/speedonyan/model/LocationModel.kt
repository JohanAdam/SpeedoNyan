package com.nyan.speedonyan.model

import com.nyan.domain.model.LocationDomainModel

data class LocationModel (
    var latitude: String,
    var longitude: String,
    var speed: String
)


fun LocationDomainModel.mapToPresentation() : LocationModel {
    return LocationModel(latitude, longitude, speed)
}