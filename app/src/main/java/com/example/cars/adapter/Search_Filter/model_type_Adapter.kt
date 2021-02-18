package com.example.cars.adapter.Search_Filter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.Model.Brand
import com.example.cars.Model.Brandtest
import com.example.cars.R
import com.example.cars.databinding.ModelItemBinding
import kotlinx.android.synthetic.main.model_item.view.*


class model_type_Adapter(
    var activity: Activity, var data: MutableList<Brand>, val itemclick: onClick
) :
    RecyclerView.Adapter<model_type_Adapter.MyViewHolder>() {


    var array = ArrayList<Brandtest>()
    class MyViewHolder(val item: ModelItemBinding) : RecyclerView.ViewHolder(item.root) {


/*
        fun bind(n: CarNewElement) {
            item.newsItem = n
            item.executePendingBindings()
        }
*/


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: ModelItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.model_item, parent, false
        )
        return MyViewHolder(itemView_layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.itemView.apply {
            txt_model_title.text = data[position].title
            txt_model_type.text =data[position].created_at


            btn_selected_model.setOnClickListener {
                itemclick.onClickItem(holder.adapterPosition,1)
            }
        }


    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }


}
