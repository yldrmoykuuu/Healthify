package com.example.fitnest

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.fitnest.databinding.ActivityDiyetisyenMapsBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.material.snackbar.Snackbar


class DiyetisyenMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDiyetisyenMapsBinding
private  lateinit var locationManager: LocationManager
private lateinit var locationListener: LocationListener
private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var isFirstLocation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiyetisyenMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyCAQ99m7-NPqVGTbgpn8r1dIPwZEuKjZsw")
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        registerLauncher()

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.uiSettings.isScrollGesturesEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true
        mMap.uiSettings.isTiltGesturesEnabled = true

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val kullaniciKonumu = LatLng(location.latitude, location.longitude)
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(kullaniciKonumu).title("Konumunuz!"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kullaniciKonumu, 14f))
                isFirstLocation = false
                // 🔽 Diyetisyenleri burada çağırmalısın
                getNearbyDietitians(location.latitude, location.longitude)
            }
        }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Snackbar.make(
                    binding.root,
                    "Konumunuzu almak için izin gerekli",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("İzin Ver") {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1
                        )
                    }.show()
            } else {
                // Direkt izin iste
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }

        } else {
            // İzin zaten varsa konumu al
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )


        }

    }

    private fun getNearbyDietitians(latitude: Double, longitude: Double) {
        val placesClient = Places.createClient(this)

        val locationBias = RectangularBounds.newInstance(
            LatLng(latitude - 0.05, longitude - 0.05),
            LatLng(latitude + 0.05, longitude + 0.05)
        )

        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery("diyetisyen")
            .setLocationBias(locationBias)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                for (prediction in response.autocompletePredictions) {
                    val placeId = prediction.placeId

                    val placeRequest = FetchPlaceRequest.builder(
                        placeId,
                        listOf(Place.Field.NAME, Place.Field.LAT_LNG)
                    ).build()

                    placesClient.fetchPlace(placeRequest)
                        .addOnSuccessListener { placeResponse ->
                            val place = placeResponse.place
                            val konum = place.latLng
                            if (konum != null) {
                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(konum)
                                        .title(place.name)
                                        .snippet("Diyetisyen")
                                )
                            }
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Diyetisyenler alınamadı", Toast.LENGTH_SHORT).show()
            }
    }

    private fun registerLauncher() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                if (ContextCompat.checkSelfPermission(
                        this@DiyetisyenMapsActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0f,
                        locationListener
                    )
                    val sonBilinenKonum =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (sonBilinenKonum != null) {
                        val sonBilinenLatLng =
                            LatLng(sonBilinenKonum.latitude, sonBilinenKonum.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sonBilinenLatLng, 14f))
                    }
                }
            } else {
                Toast.makeText(
                    this@DiyetisyenMapsActivity,
                    "İzne ihtiyacınız var",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }




}



