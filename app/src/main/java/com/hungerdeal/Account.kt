package com.hungerdeal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_account.view.*

class Account:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val manager=Manager(context!!)
        val view= inflater.inflate(R.layout.fragment_account, container, false)
        view.logout.setOnClickListener {
            manager.deleteAppData()
            startActivity(Intent(context!!, MainActivity::class.java))
        }
        return view
    }
}