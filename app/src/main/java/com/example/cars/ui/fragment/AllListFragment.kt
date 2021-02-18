package com.example.cars.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.cars.Model.CarNewElement
import com.example.cars.Model.myApp
import com.example.cars.R
import com.example.cars.adapter.AllCarAdapter
import com.example.cars.other.*
import com.example.cars.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.all_list_fragment.view.*

/**
 * A simple [Fragment] subclass.
 */
class AllListFragment : Fragment() , AllCarAdapter.onClick{


    val args: AllListFragmentArgs by navArgs()


    var array = ArrayList<CarNewElement>()
    val search_array = ArrayList<CarNewElement>()

    private val viewModel: CarViewModel by lazy {
        ViewModelProvider(this).get(CarViewModel::class.java)
    }

    private val adapter by lazy {
        AllCarAdapter(
            requireActivity(),
            array,
            this,
            NEW
        )
    }

    private val used_adapter by lazy {
        AllCarAdapter(
            requireActivity(),
            array,
            this,
            USED
        )
    }


    lateinit var root:View

    override fun onStart() {
        myApp.showLoadingDialog(requireActivity())
        Refresh_data()
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.all_list_fragment, container, false
        )
        root = binding.root

        root.swipe_to_refresh_list.setOnRefreshListener {
            search_array.clear()
            Refresh_data()
            root.swipe_to_refresh_list.isRefreshing =false
        }


        return root
    }

    override fun onClickItem(position: Int, type: Int) {
        when(type){
            1->{
                move_to_details(position)
            }
        }
    }

    private fun Refresh_data(){
        if (args.type == NEWCAR){
            viewModel.carsNewElement.observe(requireActivity(), Observer {
                adapter.setCar(it)
                array = it as ArrayList<CarNewElement>
                root.list_all.adapter =  adapter
                adapter.notifyDataSetChanged()
                myApp.dialog.hide()
            })
        }else if (args.type == USEDCAR){
            viewModel.carsOldElement.observe(requireActivity(), Observer {
                used_adapter.setCar(it)
                array = it as ArrayList<CarNewElement>
                root.list_all.adapter =  used_adapter
                used_adapter.notifyDataSetChanged()
                myApp.dialog.hide()
            })
        }else{
            Log.e("$TAG arg",args.choice)
            viewModel.carsNewElement.observe(requireActivity(), Observer {
                it.forEach {
                    if (it.brand.title == args.choice){
                        search_array.add(it)
                    }
                }
                used_adapter.setCar(search_array)
                root.list_all.adapter =  used_adapter
                used_adapter.notifyDataSetChanged()
            }).also {
                viewModel.carsOldElement.observe(requireActivity(), Observer {
                    it.forEach {
                        if (it.brand.title == args.choice) {
                            search_array.add(it)
                        }
                    }
                    used_adapter.setCar(search_array)
                    root.list_all.adapter = used_adapter
                    used_adapter.notifyDataSetChanged()
                    myApp.dialog.hide()
                })
            }.also {
                myApp.dialog.hide()
            }
            Log.e(TAG,"no search result")
        }
    }

    private fun move_to_details(position: Int){
        val action = AllListFragmentDirections.actionAllListFragmentToDetailsFragment(array[position])
        Navigation.findNavController(root).navigate(action)
    }


}
