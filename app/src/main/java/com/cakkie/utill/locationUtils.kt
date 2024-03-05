package com.cakkie.utill

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.cakkie.utill.locationModels.LocationResult
import com.cakkie.utill.locationModels.Place


fun Activity.isLocationPermissionGranted(): Boolean {
    return if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            0
        )
        false
    } else {
        true
    }
}

//get current location using fused location provider
@SuppressLint("MissingPermission")
fun Activity.getCurrentLocation(): Location? {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val providers = locationManager.getProviders(true)
    var bestLocation: Location? = null
    if (isLocationPermissionGranted()) {
        for (provider in providers) {
            val l = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                bestLocation = l
            }
        }
    }
    return bestLocation
}


//get address from location
fun Context.getAddressFromLocation(location: Location): Address? {
    val geocoder = android.location.Geocoder(this)
    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//    Timber.d("address: $addresses")
    return addresses?.get(0)
}

//get nearby address from location
fun Context.getNearbyAddressFromLocation(location: Location): List<Address> {
    val geocoder = android.location.Geocoder(this)
    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 5)
//    Timber.d("address: $addresses")
    return addresses ?: listOf()
}

//search address within location
fun Context.searchAddressFromLocation(location: Location, query: String): List<Address> {
    val geocoder = android.location.Geocoder(this)
    val addresses = geocoder.getFromLocationName(
        query,
        5,
        location.latitude - 0.1,
        location.longitude - 0.1,
        location.latitude + 0.1,
        location.longitude + 0.1
    )
//    Timber.d("address: $addresses")
    return addresses ?: listOf()
}


fun getCurrentAddress(lat: Double, lng: Double): LocationResult? {
    var _locationResult: LocationResult? = null
    NetworkCalls.get<Place>(
        endpoint = Endpoints.GET_LOCATION(lat, lng),
        body = listOf()
    ).addOnSuccessListener { locationResult ->
        _locationResult = locationResult.results.first()
    }

    return _locationResult
}

fun getNearbyAddress(lat: Double, lng: Double): List<LocationResult> {
    var _locationResult: List<LocationResult> = listOf()
    NetworkCalls.get<Place>(
        endpoint = Endpoints.GET_LOCATION(lat, lng),
        body = listOf()
    ).addOnSuccessListener { locationResult ->
        _locationResult = locationResult.results
    }

    return _locationResult
}