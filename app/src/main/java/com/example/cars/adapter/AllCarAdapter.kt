package com.example.cars.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.Model.CarNewElement
import com.example.cars.Network.RestClint
import com.example.cars.R
import com.example.cars.databinding.CarItemAllBinding
import com.example.news_app.model.Generic.FooWrapper
import com.google.gson.reflect.TypeToken
import com.loopj.android.http.JsonHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.car_item_all.view.*
import kotlinx.android.synthetic.main.search_fragment.*
import org.json.JSONObject


class AllCarAdapter(
    var activity: Activity, var data: MutableList<CarNewElement>, val itemclick: onClick, val type:String
) :
    RecyclerView.Adapter<AllCarAdapter.MyViewHolder>(), Filterable {

    val generator = FooWrapper()

    val rest=RestClint()
    var mdata: MutableList<CarNewElement> = data

    class MyViewHolder(val item: CarItemAllBinding) : RecyclerView.ViewHolder(item.root) {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: CarItemAllBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.car_item_all, parent, false
        )
        return MyViewHolder(
            itemView_layout
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setCar(lsit: List<CarNewElement>) {
        this.data = lsit as MutableList<CarNewElement>
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.itemView.apply {

            Thread.sleep(200)
            all_item_card.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.slide_left))

            if (data[position].title == "") {
                txt_title_all.text = "car"
            }else{
                txt_title_all.text = data[position].title
            }
            Picasso.get().load(data[position].main_image).into(tv_image_all)
            txt_desc_all.text= data[position].price
            all_item_card.setOnClickListener {
                itemclick.onClickItem(holder.adapterPosition,1)
            }
            txt_brand_all.text = data[position].brand.title +" "+ data[position].year
            if (type == "used") {
                txt_mileage_all.text = data[position].mileage + " Km"
            }else{
                txt_mileage_all.visibility = View.GONE
            }

        }

    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                data = results!!.values as ArrayList<CarNewElement>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charString: String = constraint.toString()
                if (charString.isEmpty())
                    data = mdata
                else {
                    val filteredList = ArrayList<CarNewElement>()

                    rest.get(RestClint.BASE_URL,object :JsonHttpResponseHandler(){

                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            response: JSONObject?
                        ) {
                            val x = response!!.getJSONObject("data")



                            val car_new=x.getJSONArray("car_new")
                            val car_used=x.getJSONArray("car_used")


                            val mutableListTutorialType =
                                object : TypeToken<MutableList<CarNewElement>>() {}.type

                            val array =generator.getResponse(car_new.toString(),
                                CarNewElement::class.java,mutableListTutorialType)

                            for (i in 0 until array.size){
                                if (array[i].title.toLowerCase().contains(charString.toLowerCase()) || array[i].brand.title.contains(charString.toLowerCase())) {
                                    filteredList.add(array[i])
                                }else if(activity.tv_check.isChecked){
                                    if (array[i].brand.title.toLowerCase() == activity.tv_check.text.toString()) {
                                        Log.e("eee search",array[i].brand.title.toLowerCase().toString())
                                        filteredList.add(array[i])
                                    }
                                }
                            }
                        }
                    })
                    data = filteredList

                }
                val filteredResult = FilterResults()
                filteredResult.values = data
                return filteredResult
            }


        }
    }





}
