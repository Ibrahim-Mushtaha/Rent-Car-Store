package com.example.cars.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.Model.CarNewElement
import com.example.cars.R
import com.example.cars.databinding.CarItemBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.car_item.view.*


class NewCarAdapter(
    var activity: Activity, var data: MutableList<CarNewElement>, val itemclick: onClick
) :
    RecyclerView.Adapter<NewCarAdapter.MyViewHolder>() {


    class MyViewHolder(val item: CarItemBinding) : RecyclerView.ViewHolder(item.root) {


/*
        fun bind(n: CarNewElement) {
            item.newsItem = n
            item.executePendingBindings()
        }
*/


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: CarItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.car_item, parent, false
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
        val currentItem = data[position]
      //  holder.bind(currentItem)

        holder.itemView.apply {

            if (data[position].title == "") {
                txt_title.text = "car"
            }else{
                txt_title.text = data[position].title
            }

            Picasso.get().load(data[position].main_image).into(tv_image)
            all_card.setOnClickListener {
                itemclick.onClickItem(holder.adapterPosition,1)
            }

            txt_brand.text = data[position].brand.title +" "+ data[position].year
            txt_mileage.visibility = View.GONE
        }


    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }


}
