package com.androavadhut.sanglidistrict

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toIcon
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androavadhut.sanglidistrict.data.SangliLatLongs
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.annotations.PolygonOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap

class SangliDistActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var buttonBackHome: Button
    private lateinit var buttonMiraj: Button
    private lateinit var mapboxMap: MapboxMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val key = "NrkwVsgcuyrvP4WtMKGT"
        val mapId = "streets-v2"
        val styleUrl = "https://api.maptiler.com/maps/$mapId/style.json?key=$key"
        val lt = 17.16052088107678
        val lg = 74.65806864287737
        val zoomLvl = 8.0
        // Init MapLibre
        //Mapbox.getInstance(this)
        // Init layout view
        val inflater = LayoutInflater.from(this)
        val rootView = inflater.inflate(R.layout.activity_sangli_dist, null)
        setContentView(rootView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Init the MapView
        mapView = rootView.findViewById(R.id.mapView)
        mapView.getMapAsync { map ->
            mapboxMap = map
            mapboxMap.setStyle(styleUrl)
            mapboxMap.cameraPosition = CameraPosition.Builder().target(LatLng(lt,lg)).zoom(zoomLvl).build()
            addMarker()
            addPolygon()
        }

        buttonBackHome = findViewById(R.id.buttonBackHome)
        buttonBackHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonMiraj = findViewById(R.id.buttonMiraj)
        buttonMiraj.setOnClickListener {
            val intent = Intent(this, MirajCountyActivity::class.java)
            startActivity(intent)
        }
    }
    fun addMarker(){
        val infoIconDrawable = ResourcesCompat.getDrawable(this.resources,R.drawable.default_marker,theme)!!
        val iconBitmap = infoIconDrawable.toBitmap()
        val icon = iconBitmap.toIcon()
        val markerIcon = IconFactory.getInstance(this).fromBitmap(iconBitmap)

        val markerLatLng = LatLng(17.16052088107678, 74.65806864287737)
        val markerOptions = MarkerOptions()
            .position(markerLatLng)
            .title("Sangli District")
            .snippet("Sangli")
            .icon(markerIcon)
        mapboxMap.addMarker(markerOptions)
    }

    fun addPolygon(){
        val latLngList = SangliLatLongs.sangliLatLongList()

        val polygonOptions = PolygonOptions()

        polygonOptions.addAll(latLngList)
        polygonOptions.strokeColor(Color.RED)
        polygonOptions.fillColor(Color.TRANSPARENT)

        mapboxMap.addPolygon(polygonOptions)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}