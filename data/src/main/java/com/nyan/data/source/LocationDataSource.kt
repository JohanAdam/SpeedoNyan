package com.nyan.data.source

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.nyan.data.model.LocationDataModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class LocationDataSource(context: FragmentActivity) {

    companion object {
        private const val TAG = "LocationDataSource"
//        val LOCATION_REQUEST_INTERVAL = 10000L
        val LOCATION_REQUEST_INTERVAL = 5000L
//        val LOCATION_REQUEST_FASTEST_INTERVAL = 5000L
        val LOCATION_REQUEST_FASTEST_INTERVAL = 10L
    }

    private val locationSubject = PublishSubject.create<LocationDataModel>()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = LOCATION_REQUEST_INTERVAL
        fastestInterval = LOCATION_REQUEST_FASTEST_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        smallestDisplacement = 0.1f
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.locations?.onEach(::setLocation)
        }
    }

    val locationObservable: Flowable<LocationDataModel> = locationSubject.toFlowable(BackpressureStrategy.MISSING)
        .doOnSubscribe { startLocationUpdates() }
        .doOnCancel { stopLocationUpdates() }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates: ")
        fusedLocationClient.lastLocation.addOnSuccessListener(::setLocation)
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        Log.d(TAG, "stopLocationUpdates: ")
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun setLocation(location: Location?) {
        Log.d(TAG, "setLocation")
        locationSubject.onNext(
            LocationDataModel(
                location?.latitude.toString(),
                location?.longitude.toString(),
                location?.speed.toString()
            )
        )
    }

}