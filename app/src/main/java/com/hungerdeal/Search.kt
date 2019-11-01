package com.hungerdeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*
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
        view.search_search.setOnClickListener {
            val keyword= view.keyword_search.text.toString().trim().toLowerCase()
            val restaurant= view.restaurant_search.text.toString().trim().toLowerCase()

            add_search.setOnClickListener{
                quantity++
                quantity_search.text=quantity.toString().trim()
            }

            remove_search.setOnClickListener{
                if (quantity>1) {
                    quantity--
                    quantity_search.text=quantity.toString().trim()
                }
            }

            progressBar_search.visibility=View.VISIBLE
            manager!!.compare(keyword, restaurant,  1).addOnCompleteListener(object :Manager.OnCompleteListener{
                override fun onComplete(task: Boolean, info: String, data:String?) {
                    progressBar_search.visibility=View.INVISIBLE
                    if (task) {
                        Toast.makeText(context!!, data, Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(context!!, info, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        return view
    }
}