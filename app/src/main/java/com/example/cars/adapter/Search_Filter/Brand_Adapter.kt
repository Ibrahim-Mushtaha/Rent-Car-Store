package com.example.cars.adapter.Search_Filter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.Model.Brandtest
import com.example.cars.R
import com.example.cars.databinding.BrandItemBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.brand_item.view.*


class Brand_Adapter(
    var activity: Activity, var data: MutableList<Brandtest>, val itemclic: onClick
) :
    RecyclerView.Adapter<Brand_Adapter.MyViewHolder>() {


    var last_position =0
    var array = ArrayList<Brandtest>()
    class MyViewHolder(val item: BrandItemBinding) : RecyclerView.ViewHolder(item.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: BrandItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.brand_item, parent, false
        )
        return MyViewHolder(itemView_layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.apply {


            Picasso.get().load(data[position].Image).into(tv_brand_image)


            if (position != last_position) {
                tv_brand_select.isChecked = false
            }

            tv_brand_select.setOnClickListener {
                tv_brand_select.isChecked =true
                last_position = holder.adapterPosition
                itemclic.onClick(holder.adapterPosition,1)
            }



        }


    }

    interface onClick {
        fun onClick(position: Int, type: Int)
    }


}
