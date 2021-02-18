package com.example.cars.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.cars.Network.RestClint
import com.example.cars.Model.User
import com.example.cars.util.myApp
import com.example.cars.R

import com.example.cars.other.*
import com.example.cars.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.guarantees_fragment.view.*

/**
 * A simple [Fragment] subclass.
 */
class GuaranteesFragment : Fragment() {


    val args: GuaranteesFragmentArgs by navArgs()

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    val rest=RestClint()
    lateinit var root:View

    override fun onStart() {
        myApp.showLoadingDialog(requireActivity())
        viewModel.getResponse(GUARANTEES,args.car.id)
        viewModel.getResponse(ROADSIDEASSISTANCE,args.car.id)
        super.onStart()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.guarantees_fragment, container, false
        )
        root = binding.root


        getData()

        root.btn_next.setOnClickListener {
            val action =
                GuaranteesFragmentDirections.actionGuaranteesFragmentToCheckoutFragment(
                    User(
                        args.user.name,
                        args.user.email,
                        args.user.phone,
                        args.user.city,
                        root.guarantees_spinner.selectedItem.toString()
                        ,
                        root.roadSide_spinner.selectedItem.toString()
                    ), args.car
                )
            Navigation.findNavController(root).navigate(action)
        }



        return root
    }


    private  fun getData(){
        viewModel.carsGuarantees.observe(requireActivity(), Observer {
            val array = ArrayList<String>()
            it.forEach {
                array.add(it.title)
            }
            val x = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, array)
            x.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            root.guarantees_spinner.setAdapter(x)
        })


        viewModel.carsRoadSide.observe(requireActivity(), Observer {
            val array = ArrayList<String>()
            it.forEach {
                array.add(it.title)
            }
            val x = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, array)
            x.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            root.roadSide_spinner.setAdapter(x).also {
                myApp.dialog.hide()
            }
        })

    }


}
