package com.hungerdeal

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_compare_coded.*
import org.json.JSONArray
import org.json.JSONObject

class CompareCoded : AppCompatActivity() {

    private val manager=Manager(this)
    private var check=0
    private var min_price=Float.MAX_VALUE
    private var min=""
    private var url=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_coded)


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

        manager.compareCoded(mealname, restaurant_name, quantity, "swiggy", object :Manager.OnCompleteListener{
            override fun onComplete(task: Boolean, info: String, data: String?) {
                swiggy_loading_compare.visibility= View.GONE
                if (task) {
                    val obj= JSONArray(data!!).getJSONObject(0)
                    imDone(obj)
                }
            }
        })
    }

    private fun imDone(data: JSONObject) {
        swiggy_price.text="Rs. "+data.getString("swiggy_price")
        zomato_price.text="Rs. "+data.getString("zomato_price")
        ubereats_price.text="Rs. "+data.getString("uber_price")

        swiggy_time.text=data.getString("swiggy_time")
        zomato_time.text=data.getString("zomato_time")
        ubereats_time.text=data.getString("uber_time")

        min = "swiggy"
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
    }
}
