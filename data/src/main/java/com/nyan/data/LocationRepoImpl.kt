package com.nyan.data

import com.nyan.data.source.LocationDataSource
import com.nyan.domain.model.LocationDomainModel
import com.nyan.domain.repo.LocationRepo
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

class LocationRepoImpl(val locationDataSource: LocationDataSource) : LocationRepo {

    override fun getLocation(): Flowable<LocationDomainModel> {
        return locationDataSource
            .locationObservable
            .map {
                it.mapToDomain()
            }
    }

}