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
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
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
        fetchLastLocation()
        return binding.root
    }

    private fun fetchLastLocation() {
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
            fetchLastLocation()
            return
        }
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
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

    private fun direction() {
        val origin = "${currentLocation?.latitude}, ${currentLocation?.longitude}"
        val destinationLat = -6.9144
        val destinationLong = 107.6024
        val mode = "driving"
        val apiKey = BuildConfig.MAPS_API_KEY
        val get = viewModel.getDirection(origin, "${destinationLat}, ${destinationLong}", mode, apiKey)
        get.observe(viewLifecycleOwner) { maps ->
            if (maps != null) {
                Log.d("TAG_MAPS", maps.message.toString())
                when (maps) {
                    is Resource.Loading<*> -> {
//                        setViewCondition(
//                            progress = true,
//                            error = false,
//                            empty = "error.json"
//                        )
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
                                        for (l in list!!.indices) {
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
//                        setViewCondition(
//                            progress = false,
//                            error = true,
//                            empty = "error.json"
//                        )
                        get.removeObservers(viewLifecycleOwner)
                    }
                }
            }
        }

//        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
//        val url: String = Uri.parse("https://maps.googleapis.com/maps/api/directions/json")
//            .buildUpon()
//            .appendQueryParameter("destination", "-6.9218571, 107.6048254")
//            .appendQueryParameter("origin", "-6.9249233, 107.6345122")
//            .appendQueryParameter("mode", "driving")
//            .appendQueryParameter("key", "YOUR_API_KEY")
//            .toString()
//        val jsonObjectRequest =
//            JsonObjectRequest(Request.Method.GET, url, null, object : Listener<JSONObject?>() {
//                fun onResponse(response: JSONObject) {
//                    try {
//                        val status = response.getString("status")
//                        if (status == "OK") {
//                            val routes = response.getJSONArray("routes")
//                            var points: ArrayList<LatLng?>
//                            var polylineOptions: PolylineOptions? = null
//                            for (i in 0 until routes.length()) {
//                                points = ArrayList()
//                                polylineOptions = PolylineOptions()
//                                val legs = routes.getJSONObject(i).getJSONArray("legs")
//                                for (j in 0 until legs.length()) {
//                                    val steps = legs.getJSONObject(j).getJSONArray("steps")
//                                    for (k in 0 until steps.length()) {
//                                        val polyline =
//                                            steps.getJSONObject(k).getJSONObject("polyline")
//                                                .getString("points")
//                                        val list = decodePoly(polyline)
//                                        for (l in list.indices) {
//                                            val position =
//                                                LatLng(list[l].latitude, list[l].longitude)
//                                            points.add(position)
//                                        }
//                                    }
//                                }
//                                polylineOptions.addAll(points)
//                                polylineOptions.width(10f)
//                                polylineOptions.color(
//                                    ContextCompat.getColor(
//                                        this@MapsActivity,
//                                        R.color.purple_500
//                                    )
//                                )
//                                polylineOptions.geodesic(true)
//                            }
//                            mMap.addPolyline(polylineOptions)
//                            mMap.addMarker(
//                                MarkerOptions().position(LatLng(-6.9249233, 107.6345122))
//                                    .title("Marker 1")
//                            )
//                            mMap.addMarker(
//                                MarkerOptions().position(LatLng(-6.9218571, 107.6048254))
//                                    .title("Marker 1")
//                            )
//                            val bounds = LatLngBounds.Builder()
//                                .include(LatLng(-6.9249233, 107.6345122))
//                                .include(LatLng(-6.9218571, 107.6048254)).build()
//                            val point = Point()
//                            getWindowManager().getDefaultDisplay().getSize(point)
//                            mMap.animateCamera(
//                                CameraUpdateFactory.newLatLngBounds(
//                                    bounds,
//                                    point.x,
//                                    150,
//                                    30
//                                )
//                            )
//                        }
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//                }
//            }, object : ErrorListener() {
//                fun onErrorResponse(error: VolleyError?) {}
//            })
//        val retryPolicy: RetryPolicy = DefaultRetryPolicy(
//            30000,
//            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        )
//        jsonObjectRequest.setRetryPolicy(retryPolicy)
//        requestQueue.add(jsonObjectRequest)
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


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;
        direction();
    }
}