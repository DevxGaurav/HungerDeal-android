package com.hungerdeal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.view.*

class Search:Fragment() {

    var manager:Manager?=null
    var quantity=1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_search, container, false)
        manager= Manager(context!!)

        view.add_search.setOnClickListener{
            quantity++
            view.quantity_search.text=quantity.toString().trim()
        }

        view.remove_search.setOnClickListener{
            if (quantity!=1) {
                quantity--
                view.quantity_search.text=quantity.toString().trim()
            }
        }

        view.search_search.setOnClickListener {
            val keyword= view.keyword_search.text.toString().trim().toLowerCase()
            val restaurant= view.restaurant_search.text.toString().trim().toLowerCase()
            val bundle=Bundle()
            bundle.putString("meal_name", keyword)
            bundle.putInt("quantity", quantity)
            bundle.putString("restaurant_name", restaurant)
            startActivity(Intent(context!!, CompareResult::class.java).putExtras(bundle))
        }
        return view
    }
}