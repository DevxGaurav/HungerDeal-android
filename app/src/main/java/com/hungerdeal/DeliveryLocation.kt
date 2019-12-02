package com.hungerdeal

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_delivery_location.*

class DeliveryLocation : AppCompatActivity() {

    private val manager=Manager(this)
    private var type="Home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_location)

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location!=null) {
                    Toast.makeText(this, location.latitude.toString()+" "+location.longitude.toString(), Toast.LENGTH_LONG).show()
                }
            }


        chip_grp_delivery_location.setOnCheckedChangeListener { p0, p1 ->
            val chip=p0!!.findViewById<Chip>(p1)
            type=chip.text.toString()
        }

        val cities=resources.getStringArray(R.array.cities)
        val adapter=ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities)
        city_location.setAdapter(adapter)
        save_location.setOnClickListener {
            val location=location_location.text.toString().trim()
            val city=city_location.text.toString().trim()
            var k=0
            for (ct in cities) {
                if (ct.toLowerCase()==city.toLowerCase()) {
                    k++
                }
            }
            when {
                location=="" -> Toast.makeText(this, "Location cannot be empty", Toast.LENGTH_LONG).show()
                location.length<4 -> Toast.makeText(this, "Looks like you are not entering a valid location!", Toast.LENGTH_LONG).show()
                k==0 -> Toast.makeText(this, "Please select a city from drop down", Toast.LENGTH_LONG).show()
                else -> {
                    manager.saveUserLocation(location).saveUserLocationType(type).saveUserLocationCity(city)
                    startActivity(Intent(this, Dashboard::class.java))
                    finish()
                }
            }
        }
    }
}
