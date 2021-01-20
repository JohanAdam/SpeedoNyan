package com.nyan.data.model

import com.nyan.domain.model.LocationDomainModel

data class LocationDataModel (
    var latitude: String,
    var longitude: String,
    var speed: String) {

    fun mapToDomain(): LocationDomainModel? {
        return LocationDomainModel(
            latitude,
            longitude,
            speed
        )
    }
}