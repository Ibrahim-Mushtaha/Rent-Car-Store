package com.example.cars.adapter.intro

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.Model.Boarding
import com.example.cars.R
import com.example.cars.databinding.OnboardingItemBinding
import kotlinx.android.synthetic.main.onboarding_item.view.*


class onBoarding_Adapter(
    var activity: Activity, var data: Array<Boarding>,val itemclick: onClick
) :
    RecyclerView.Adapter<onBoarding_Adapter.MyViewHolder>() {


    class MyViewHolder(val item: OnboardingItemBinding) : RecyclerView.ViewHolder(item.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: OnboardingItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.onboarding_item, parent, false
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
            boarding_image.setImageResource(data[position].Image)
            txt_boarding_title.text =data[position].title
            if (position == 2 || position ==3){
                boarding_btn.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.slide_left)).also {
                    boarding_btn.visibility =View.VISIBLE
                }
            }else{
                boarding_btn.visibility =View.GONE
            }

            boarding_btn.setOnClickListener {
                itemclick.onClickItem(holder.adapterPosition,1)
            }
        }


    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }


}
