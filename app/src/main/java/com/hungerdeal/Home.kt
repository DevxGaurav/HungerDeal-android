package com.hungerdeal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_item_adapter.view.*
import org.json.JSONArray

class Home: Fragment() {

    private var manager:Manager?=null
    private var cart=ArrayList<Dishes>()
    private var quantity=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        val list=ArrayList<Dishes>()
        manager=Manager(context!!)
        val data=JSONArray(manager!!.getAppData())
        for (i in 0 until data.length()) {
            val obj=data.getJSONObject(i)
            list.add(Dishes(obj.getString("meal_id").toInt(), obj.getString("meal_name"), obj.getString("meal_price"), obj.getString("cuisine"), obj.getString("veg").toInt(), obj.getString("healthy").toInt(), obj.getString("picture_url"), obj.getString("restaurant_name"), obj.getString("restaurant_stars"), obj.getString("restaurant_url"), obj.getString("location")))
        }
        val adapter=Adapter(list, view)
        view.recycler_home.layoutManager=LinearLayoutManager(context!!)
        view.recycler_home.adapter=adapter
        adapter.notifyDataSetChanged()
        view.address_main.text=manager!!.getUserLocationType()
        view.address_heading.text=manager!!.getUserLocation()
        view.compare_button.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("meal_name", cart.get(0).meal_name)
            bundle.putString("meal_price", cart.get(0).meal_price)
            bundle.putInt("quantity", quantity)
            bundle.putString("outlet", cart.get(0).location)
            bundle.putString("restaurant_name", cart.get(0).restaurant_name)
            startActivity(Intent(context!!, CompareResult::class.java).putExtras(bundle))
        }
        return view
    }



    private inner class Dishes(val meal_id:Int, val meal_name: String, val meal_price:String, val cuisine:String, val veg:Int, val healthy:Int, val url:String, val restaurant_name:String, val restaurant_stars:String, val restaurant_url:String, val location:String)

    private inner class Adapter(val list:ArrayList<Dishes>, val view:View): RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.fragment_item_adapter, parent, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.name.text=list[position].meal_name
            holder.outlet.text=list[position].restaurant_name
            holder.price.text="Rs. "+list[position].meal_price
            Glide.with(context!!).load(list[position].url).into(holder.icon)
            holder.add.setOnClickListener {
                holder.quantity_++
                quantity=holder.quantity_
                holder.quantity.text=holder.quantity_.toString()
                view.compare_button.visibility=View.VISIBLE
                cart.add(list[position])
                var price=list[position].meal_price.toFloat()*holder.quantity_
                view.price_approx.text="Rs. "+price.toString()
            }
            holder.remove.setOnClickListener {
                if (holder.quantity_!=0) {
                    holder.quantity_--
                    quantity=holder.quantity_
                    holder.quantity.text=holder.quantity_.toString()
                    var price=list[position].meal_price.toFloat()*holder.quantity_
                    view.price_approx.text="Rs. "+price.toString()
                }
                if (holder.quantity_==0) {
                    view.compare_button.visibility=View.GONE
                    cart.remove(list[position])
                }
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val icon=itemView.icon_item_adapter
            val name=itemView.name_item_adapter
            val outlet=itemView.outlet_item_adapter
            val price=itemView.price_item_adapter
            val add=itemView.add_item_adapter
            val remove=itemView.remove_item_adapter
            val quantity=itemView.quantity_item_adapter
            var quantity_=0
        }
    }
}