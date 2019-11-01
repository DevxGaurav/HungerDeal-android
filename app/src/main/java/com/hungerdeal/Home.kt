package com.hungerdeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONArray

class Home: Fragment() {

    var manager:Manager?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        manager=Manager(context!!)
        val data=JSONArray(manager!!.getAppData())

        view.address_main.text=manager!!.getUserLocationType()
        view.address_heading.text=manager!!.getUserLocation()
        return view
    }
}