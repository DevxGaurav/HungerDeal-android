package com.hungerdeal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_apps.view.*

class Apps:Fragment() {

    private var manager:Manager?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_apps, container, false)
        manager= Manager(context!!)
        view.app1_icon.setOnClickListener {
            val i1 = Intent(
                Intent.ACTION_VIEW, Uri.parse("https://www.zomato.com/hi/mountain-view-ca/order?lang=hi")
            )
            startActivity(i1)
        }
        view.app2_icon.setOnClickListener {
            val i2 = Intent(
                Intent.ACTION_VIEW, Uri.parse("https://www.ubereats.com/en-IN/")
            )
            startActivity(i2)
        }
        view.app3_icon.setOnClickListener {
            val i3 = Intent(
                Intent.ACTION_VIEW, Uri.parse("https://www.swiggy.com")
            )
            startActivity(i3)
        }
        view.app4_icon.setOnClickListener {
            val i4 = Intent(
                Intent.ACTION_VIEW, Uri.parse("https://www.freshmenu.com/")
            )
            startActivity(i4)
        }
        view.app5_icon.setOnClickListener {
            val i5 = Intent(
                Intent.ACTION_VIEW, Uri.parse("https://delhi.foodpanda.in/")
            )
            startActivity(i5)
        }
        view.app6_icon.setOnClickListener {
            val i6 = Intent(
                Intent.ACTION_VIEW, Uri.parse("https://www.dominos.co.in/")
            )
            startActivity(i6)
        }

        view.address_main.text=manager!!.getUserLocationType()
        view.address_heading.text=manager!!.getUserLocation()
        return view
    }
}