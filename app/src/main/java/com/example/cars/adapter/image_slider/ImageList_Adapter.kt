package com.example.cars.adapter.image_slider

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.R
import com.example.cars.databinding.CarImageItemBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.car_image_item.view.*


class ImageList_Adapter(
    var activity: Activity, var data: List<String>
) :
    RecyclerView.Adapter<ImageList_Adapter.MyViewHolder>() {


    class MyViewHolder(val item: CarImageItemBinding) : RecyclerView.ViewHolder(item.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: CarImageItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.car_image_item, parent, false
        )
        return MyViewHolder(
            itemView_layout
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }




    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
     /*   val currentItem = data[position]
        holder.bind(currentItem)*/

        holder.itemView.apply {
            Picasso.get().load(data[position]).into(image)
        }


    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }


}
