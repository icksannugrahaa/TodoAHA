package com.icksan.todoaha.main.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.Task
import com.icksan.todoaha.BuildConfig
import com.icksan.todoaha.R
import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.utils.GlobalUtils
import com.icksan.todoaha.databinding.FragmentMapsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapsViewModel by viewModel()
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location? = null

    companion object {
        const val REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            GlobalUtils.showToast("Please reopen!", requireContext())
            fetchLastLocation()
            return binding.root
        }
        fetchLastLocation()
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        direction()
    }

    private fun fetchLastLocation() {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                Log.d("TAG_MAPSS", location.toString())
                currentLocation = location
                Toast.makeText(
                    context, currentLocation!!.latitude
                        .toString() + "" + currentLocation!!.longitude, Toast.LENGTH_SHORT
                ).show()
                val supportMapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                supportMapFragment!!.getMapAsync(this)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun direction() {
        val origin = "${currentLocation?.latitude}, ${currentLocation?.longitude}"
        Log.d("TAG_MAPS", origin)
        val destinationLat = -6.9144
        val destinationLong = 107.6024
        val mode = "driving"
        val apiKey = BuildConfig.MAPS_API_KEY
        val get = viewModel.getDirection(origin, "$destinationLat, $destinationLong", mode, apiKey)
        get.observe(viewLifecycleOwner) { maps ->
            if (maps != null) {
                when (maps) {
                    is Resource.Loading<*> -> {
                    }
                    is Resource.Success<*> -> {
                        if(maps.data?.status == "OK") {
                            val routes = maps.data.directionRouteModels
                            var points: ArrayList<LatLng?>
                            var polylineOptions: PolylineOptions? = null
                            for (i in 0 until routes?.size!!) {
                                points = ArrayList()
                                polylineOptions = PolylineOptions()
                                val legs = routes[i].legs
                                for (j in 0 until legs?.size!!) {
                                    val steps = legs[j].steps
                                    for (k in 0 until steps?.size!!) {
                                        val polyline = steps[k].polyline?.points!!
                                        val list = decodePoly(polyline)
                                        for (l in list.indices) {
                                            val position =
                                                LatLng(list[l].latitude, list[l].longitude)
                                            points.add(position)
                                        }
                                    }
                                }
                                polylineOptions.addAll(points)
                                polylineOptions.width(10f)
                                polylineOptions.color(
                                    requireActivity().getColor(R.color.blue_500)
                                )
                                polylineOptions.geodesic(true)
                            }
                            mMap.addPolyline(polylineOptions!!)
                            mMap.addMarker(
                                MarkerOptions().position(LatLng(currentLocation?.latitude!!,
                                    currentLocation?.longitude!!
                                ))
                                    .title("Marker 1")
                            )
                            mMap.addMarker(
                                MarkerOptions().position(LatLng(destinationLat, destinationLong))
                                    .title("Marker 1")
                            )
                            val bounds = LatLngBounds.Builder()
                                .include(LatLng(currentLocation?.latitude!!,
                                    currentLocation?.longitude!!))
                                .include(LatLng(destinationLat, destinationLong)).build()
                            val point = Point()
                            requireActivity().windowManager.defaultDisplay.getSize(point)
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngBounds(
                                    bounds,
                                    point.x,
                                    150,
                                    30
                                )
                            )
                        }
                        get.removeObservers(viewLifecycleOwner)
                    }
                    is Resource.Error<*> -> {
                        get.removeObservers(viewLifecycleOwner)
                    }
                }
            }
        }
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly: MutableList<LatLng> = ArrayList()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }
        return poly
    }
}