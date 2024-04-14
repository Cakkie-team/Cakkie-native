package com.cakkie.utill

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.cakkie.utill.locationModels.LocationResult
import com.cakkie.utill.locationModels.Place
import com.cakkie.utill.locationModels.SearchResults
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


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
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        // Alert user to switch Location on in Settings
        val builder = AlertDialog.Builder(this)

        // Set the alert dialog title
        builder.setTitle("Turn on \"Location\"")

        // Display a message on alert dialog
        builder.setMessage("\"Location\" is currently turned off. Turn on \"Location\" to enable cakkie to determine your location.")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("OK") { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        // Display a negative button on alert dialog
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
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


suspend fun getCurrentAddress(lat: Double, lng: Double): LocationResult? {
    return suspendCoroutine { continuation ->
        NetworkCalls.get<Place>(
            endpoint = Endpoints.GET_LOCATION(lat, lng),
            body = listOf()
        ).addOnSuccessListener { place ->
            val locationResult = place.results.firstOrNull()
            continuation.resume(locationResult)
        }.addOnFailureListener { exception ->
            // Handle failure, maybe log the error or return null
            continuation.resume(null)
        }
    }
}

suspend fun getNearbyAddress(lat: Double, lng: Double): List<LocationResult> {
    return suspendCoroutine { continuation ->
        NetworkCalls.get<Place>(
            endpoint = Endpoints.GET_LOCATION(lat, lng),
            body = listOf()
        ).addOnSuccessListener { place ->
            val locationResults = place.results
            continuation.resume(locationResults)
        }.addOnFailureListener { exception ->
            // Handle failure, maybe log the error or return an empty list
            continuation.resumeWithException(exception)
        }
    }
}

suspend fun searchAddress(lat: Double, lng: Double, query: String): List<LocationResult> {
    return suspendCoroutine { continuation ->
        NetworkCalls.get<SearchResults>(
            endpoint = Endpoints.SEARCH_LOCATION(query, lat, lng),
            body = listOf()
        ).addOnSuccessListener { locationResult ->
            val locationResults = locationResult.predictions.map {
                LocationResult(
                    formattedAddress = it.description,
                    placeId = it.placeId,
                    geometry = null
                )
            }
            continuation.resume(locationResults)
        }.addOnFailureListener { exception ->
            // Handle failure, maybe log the error or return an empty list
            continuation.resumeWithException(exception)
        }
    }
}

suspend fun getPlaceDetails(address: String): LocationResult? {
    return suspendCoroutine { continuation ->
        NetworkCalls.get<Place>(
            endpoint = Endpoints.GET_ADDRESS(address),
            body = listOf()
        ).addOnSuccessListener { place ->
            val locationResult = place.results.firstOrNull()
            continuation.resume(locationResult)
        }.addOnFailureListener { exception ->
            // Handle failure, maybe log the error or return null
            continuation.resume(null)
        }
    }
}