package com.hungerdeal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_compare_result.*
import org.json.JSONObject


class CompareResult : AppCompatActivity() {

    private val manager=Manager(this)
    private var check=0
    private var min_price=Float.MAX_VALUE
    private var min=""
    private var url=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_result)

        address_main.text=manager.getUserLocationType()
        address_heading.text=manager.getUserLocation()
        back.setOnClickListener{
            finish()
        }

        val bundle=intent.extras
        val quantity=bundle!!.getInt("quantity")
        val mealname= bundle.getString("meal_name")!!
        val price= bundle.getString("meal_price")
        val restaurant_name= bundle.getString("restaurant_name")!!
        val outlet= bundle.getString("outlet")
        restaurant.text=restaurant_name
        item_quantity.text=quantity.toString()
        food_name.text=mealname
        price_food.text="Rs. "+price
        outlet_compare.text=outlet

        manager.compare(mealname, restaurant_name, quantity, "swiggy", object :Manager.OnCompleteListener{
            override fun onComplete(task: Boolean, info: String, data: String?) {
                swiggy_loading_compare.visibility=View.GONE
                if (task) {
                    val obj=JSONObject(data!!)
                    swiggy_price.text="Rs. "+obj.getString("total_price")
                    swiggy_time.text=obj.getString("delivery_time")
                    if (obj.getString("total_price").toFloat()<min_price) {
                        min_price=obj.getString("total_price").toFloat()
                        min="swiggy"
                        url=obj.getString("url")
                    }
                    swiggy_photo.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(obj.getString("url"))))
                    }
                    imDone()
                }else {
                    swiggy_price.text="Failed"
                    swiggy_time.text="Failed"
                    imDone()
                }
            }
        })


        manager.compare(mealname, restaurant_name, quantity, "ubereats", object :Manager.OnCompleteListener{
            override fun onComplete(task: Boolean, info: String, data: String?) {
                ubereats_loading_compare.visibility=View.GONE
                if (task) {
                    val obj=JSONObject(data!!)
                    ubereats_price.text="Rs. "+obj.getString("total_price")
                    ubereats_time.text=obj.getString("delivery_time")
                    if (obj.getString("total_price").toFloat()<min_price) {
                        min_price=obj.getString("total_price").toFloat()
                        min="ubereats"
                        url=obj.getString("url")
                    }
                    ubereats_photo.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(obj.getString("url"))))
                    }
                    imDone()
                }else {
                    ubereats_price.text="Failed"
                    ubereats_time.text="Failed"
                    imDone()
                }
            }
        })

        manager.compare(mealname, restaurant_name, quantity, "zomato", object :Manager.OnCompleteListener{
            override fun onComplete(task: Boolean, info: String, data: String?) {
                zomato_loading_compare.visibility= View.GONE
                if (task) {
                    val obj=JSONObject(data!!)
                    zomato_price.text="Rs. "+obj.getString("total_price")
                    zomato_time.text=obj.getString("delivery_time")
                    if (obj.getString("total_price").toFloat()<min_price) {
                        min_price=obj.getString("total_price").toFloat()
                        min="zomato"
                        url=obj.getString("url")
                    }
                    zomato_photo.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(obj.getString("url"))))
                    }
                    imDone()
                }else {
                    zomato_price.text="Failed"
                    zomato_time.text="Failed"
                    imDone()
                }
            }
        })
    }


    private fun imDone() {
        ++check
        if (check==3) {
            if (min=="zomato") {
                zomato_price.setTextColor(resources.getColor(R.color.colorPrimary, theme))
                zomato_time.setTextColor(resources.getColor(R.color.colorPrimary, theme))
                var params=zomato_photo.layoutParams
                params.height=220
                params.width=220
                zomato_photo.layoutParams=params
                bottom_order.visibility=View.VISIBLE
                bottom_order.setBackgroundColor(resources.getColor(R.color.zomato, theme))
                icon_to_order.setImageDrawable(resources.getDrawable(R.drawable.zomato, theme))
            }else if (min=="swiggy") {
                swiggy_price.setTextColor(resources.getColor(R.color.colorPrimary, theme))
                swiggy_time.setTextColor(resources.getColor(R.color.colorPrimary, theme))
                var params=swiggy_photo.layoutParams
                params.height=220
                params.width=220
                swiggy_photo.layoutParams=params
                bottom_order.visibility=View.VISIBLE
                bottom_order.setBackgroundColor(resources.getColor(R.color.swiggy, theme))
                icon_to_order.setImageDrawable(resources.getDrawable(R.drawable.swiggy, theme))
            }else if (min=="ubereats") {
                ubereats_price.setTextColor(resources.getColor(R.color.colorPrimary, theme))
                ubereats_time.setTextColor(resources.getColor(R.color.colorPrimary, theme))
                var params=ubereats_photo.layoutParams
                params.height=220
                params.width=220
                ubereats_photo.layoutParams=params
                bottom_order.visibility=View.VISIBLE
                bottom_order.setBackgroundColor(resources.getColor(R.color.ubereats, theme))
                icon_to_order.setImageDrawable(resources.getDrawable(R.drawable.ubereats, theme))
            }

            bottom_order.setOnClickListener{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    }
}
