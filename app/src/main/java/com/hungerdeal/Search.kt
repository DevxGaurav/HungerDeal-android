package com.hungerdeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class Search:Fragment() {

    var manager:Manager?=null
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
            progressBar_search.visibility=View.VISIBLE
            manager!!.compare(keyword, restaurant, "iiitd delhi", 1).addOnCompleteListener(object :Manager.OnCompleteListener{
                override fun onComplete(task: Boolean, info: String) {
                    progressBar_search.visibility=View.INVISIBLE
                    if (task) {
                        response_search.text=info
                    }
                }
            })
        }
        return view
    }
}