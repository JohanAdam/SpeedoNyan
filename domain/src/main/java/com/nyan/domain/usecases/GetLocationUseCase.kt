package com.nyan.domain.usecases

import com.nyan.domain.model.LocationDomainModel
import com.nyan.domain.repo.LocationRepo
import io.reactivex.Flowable

class GetLocationUseCase(private val repository : LocationRepo) {

    fun execute() : Flowable<LocationDomainModel> {
        return repository.getLocation()
    }

}