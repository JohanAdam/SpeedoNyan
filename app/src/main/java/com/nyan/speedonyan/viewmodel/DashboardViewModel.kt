package com.nyan.speedonyan.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.nyan.data.livedata.SingleLiveEvent
import com.nyan.data.model.LocationDataModel
import com.nyan.domain.usecases.GetLocationUseCase
import com.nyan.speedonyan.BaseViewModel
import com.nyan.speedonyan.model.LocationModel
import com.nyan.speedonyan.model.mapToPresentation
import com.nyan.speedonyan.scheduler.BaseSchedulerProvider
import com.nyan.speedonyan.utils.PermissionManager

class DashboardViewModel
@ViewModelInject
constructor(
    private val getLocationUseCase: GetLocationUseCase,
    baseSchedulerProvider: BaseSchedulerProvider,
    @Assisted private val savedStateHandle: SavedStateHandle) : BaseViewModel(baseSchedulerProvider) {

    companion object {
        private val TAG = "DashboardViewModel"
    }

    private val _location = MutableLiveData<LocationModel>()
    val location : LiveData<LocationModel> get() = _location

    private val _navigation = SingleLiveEvent<Navigation>()
    val navigation : LiveData<Navigation> get() = _navigation


    val mainHandler = Handler(Looper.getMainLooper())
    private var _updateCounter = MutableLiveData<Int>()
    val updateCounter : LiveData<Int> get() = _updateCounter

    init {
        mainHandler.post(object : Runnable {
            override fun run() {
//                if (_updateCounter != null) {
                _updateCounter.postValue(_updateCounter.value?.plus(1))
                mainHandler.postDelayed(this, 500)
//                }
            }
        })
    }

    fun onRequestPermissionResult(requestCode: Int, grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionResult: ")
        if (requestCode == PermissionManager.LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            when (grantResults.first()) {
                PermissionManager.PERMISSION_GRANTED -> onLocationPermissionGranted()
                PermissionManager.PERMISSION_DENIED -> onLocationPermissionDenied()
            }
        }
    }

    fun onLocationPermissionGranted() {
        Log.d(TAG, "onLocationPermissionGranted: ")
        getLocationUseCase
            .execute()
            .map { it.mapToPresentation() }
            .baseSubscribe(
                onSuccess = ::onGetLocationSuccess,
                onError = ::onGetLocationError
            )

    }

    private fun onLocationPermissionDenied() {
        Log.d(TAG, "onLocationPermissionDenied: ")
        _navigation.postValue(Navigation.Finish)
    }

    private fun onGetLocationSuccess(locationModel: LocationModel) {
        Log.d(TAG, "onGetLocationSuccess: ")
        _updateCounter.postValue(0)
        _location.postValue(locationModel)
    }

    private fun onGetLocationError(throwable: Throwable) {
        Log.d(TAG, "onGetLocationError: ")
        Log.e(DashboardViewModel::class.simpleName, "onGetLocationError " + throwable.message.orEmpty())
    }

    override fun onCleared() {
        super.onCleared()
//        mainHandler.removeCallbacks()
    }

    sealed class Navigation {
        object Finish : Navigation()
    }
}