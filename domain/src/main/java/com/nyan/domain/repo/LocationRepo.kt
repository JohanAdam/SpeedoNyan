package com.nyan.domain.repo

import com.nyan.domain.model.LocationDomainModel
import io.reactivex.Flowable

interface LocationRepo {

    fun getLocation() : Flowable<LocationDomainModel>

}