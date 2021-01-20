package com.nyan.speedonyan.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionManager constructor() {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 0x0001
        const val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED
        const val PERMISSION_DENIED = PackageManager.PERMISSION_DENIED
    }

    fun isLocationPermissionGranted(context: Context) : Boolean {
        return isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun requestLocationPermission(fragment: Fragment) {
        requestPermission(fragment, Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun requestPermission(fragment: Fragment, permission: String, requestCode: Int) {
        fragment.requestPermissions(arrayOf(permission), requestCode)
    }

    private fun isPermissionGranted(context: Context, permission: String) : Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}