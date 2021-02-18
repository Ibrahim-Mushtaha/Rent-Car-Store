package com.example.cars.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.cars.R
import kotlinx.android.synthetic.main.checkout_fragment.view.*

class CheckoutFragment : Fragment() {

    val args: CheckoutFragmentArgs by navArgs()

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.checkout_fragment, container, false)
        root.apply {
            txt_checkout_username.text = args.userInformatino.name
            txt_checkout_email.text = args.userInformatino.email
            txt_checkout_phone.text = args.userInformatino.phone
            txt_checkout_city.text = args.userInformatino.city
            txt_checkout_car_garantie.text = args.userInformatino.guarantees
            txt_checkout_car_roadSide.text = args.userInformatino.road_assistance
            txt_checkout_car_brand.text = args.formD.brand.title
            txt_checkout_car_model.text = args.formD.model.title
            txt_checkout_car_price.text = args.formD.price
        }

        root.btn_back.setOnClickListener {
           val action =
               CheckoutFragmentDirections.actionCheckoutFragmentToGuaranteesFragment(
                   args.userInformatino,
                   args.formD
               )
            Navigation.findNavController(root).navigate(action)
        }

        return root
    }

}
