package com.hungerdeal

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val manager= Manager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loaddata()
    }

    fun loaddata() {
        progressBar_main.visibility= View.VISIBLE
        manager.launchData().addOnCompleteListener(object :Manager.OnCompleteListener{
            override fun onComplete(task: Boolean, info: String, data: String?) {
                progressBar_main.visibility=View.INVISIBLE
                if (task) {
                    if (manager.getUserLocation()==null) {
                        startActivity(Intent(this@MainActivity, DeliveryLocation::class.java))
                    }else {
                        startActivity(Intent(this@MainActivity, Dashboard::class.java))
                    }
                    finish()
                }else {
                    Snackbar.make(logo_main, info, Snackbar.LENGTH_INDEFINITE).setAction("retry") {
                        loaddata()
                    }.show()
                }
            }
        })
    }
}