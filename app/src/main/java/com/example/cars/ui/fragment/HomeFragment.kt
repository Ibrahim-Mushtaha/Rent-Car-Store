package com.example.cars.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.cars.Model.CarNewElement
import com.example.cars.Model.Main_model.brand_test
import com.example.cars.Model.myApp
import com.example.cars.R
import com.example.cars.adapter.NewCarAdapter
import com.example.cars.adapter.Search_Filter.Brand_Adapter
import com.example.cars.adapter.Search_Filter.model_type_Adapter
import com.example.cars.adapter.UsedCarAdapter
import com.example.cars.other.*
import com.example.cars.viewmodel.CarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loopj.android.http.RequestParams
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_search.*
import kotlinx.android.synthetic.main.car_model_dialog.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() , NewCarAdapter.onClick,IPickResult,Brand_Adapter.onClick{

    private val viewModel:CarViewModel by lazy {
        ViewModelProvider(this).get(CarViewModel::class.java)
    }


    var array = ArrayList<CarNewElement>()
    var used_car = ArrayList<CarNewElement>()
    var brand_array_api = ArrayList<brand_test>()
    var selected_array = ArrayList<brand_test>()

    private val adapter by lazy {
        NewCarAdapter(requireActivity(), array, this)
    }

    private val adapter_selected by lazy {
        Brand_Adapter(requireActivity(),brand_array_api,this)
    }


    private val used_adapter by lazy {
        UsedCarAdapter(
            requireActivity(),
            used_car,
            object : UsedCarAdapter.onClick {
                override fun onClickItem(position: Int, type: Int) {
                    move_to_details(position)
                }

            })
    }

    lateinit var file:File

    lateinit var root:View

    override fun onStart() {
        myApp.showLoadingDialog(requireActivity())
        getDate()
        super.onStart()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().toolbar.visibility=View.VISIBLE
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.home_fragment, container, false
        )
        root = binding.root

        setHasOptionsMenu(true)
        root.view_new_car.adapter =adapter
        root.view_used_car.adapter =used_adapter
        root.indicator_home.setViewPager2(root.view_new_car)



        root.swipe_to_refresh.setOnRefreshListener {
            myApp.showLoadingDialog(requireActivity())
            getDate()
            root.swipe_to_refresh.isRefreshing = false
        }


        root.btn_uploud_home.setOnClickListener {
            openChooseImage()
        }

        root.show_all_new.setOnClickListener {
         Move_to_all_list(NEWCAR,"","","")
        }

        root.show_all_used.setOnClickListener {
            Move_to_all_list(USEDCAR,"","","")
        }

        return root
    }

    private fun Move_to_all_list(type:String,choice:String,modeltype:String,filterType:String){
        val action = HomeFragmentDirections.actionHomeFragmentToAllListFragment(type,choice,modeltype,filterType)
        Navigation.findNavController(root).navigate(action)
    }

    override fun onClickItem(position: Int, type: Int) {
            when(type){
                1->{
                    move_to_details(position)
                }
            }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.basic,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search->{
                showFilterDialog()
            }
            R.id.scan->{
                val action =HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                Navigation.findNavController(root).navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getDate(){
        //news car
        viewModel.carsNewElement.observe(requireActivity(), Observer {
            array = it as ArrayList<CarNewElement>
            adapter.setCar(it)
            adapter.notifyDataSetChanged()
            brand_array_api.clear()
           for (i in 0 until it.size){
               if (brand_array_api.size !=0){
               for (i in 0 until brand_array_api.size){
                   if (it[i].brand.title == brand_array_api[i].title){
                       Log.e("eee item","item exit")
                   }
               }
               }else{
               brand_array_api.add(
                   brand_test(
                       it[i].brand.id,
                       it[i].brand.title,
                       it[i].brand.image
                   )
               )
                   }
           }
        }).also {
            //used car
            viewModel.carsOldElement.observe(requireActivity(), Observer {
                used_car = it as ArrayList<CarNewElement>
                used_adapter.setCar(it)
                used_adapter.notifyDataSetChanged()
                myApp.dialog.hide()
                for (i in 0 until it.size){
                    brand_array_api.add(
                        brand_test(
                            it[i].brand.id,
                            it[i].brand.title,
                            it[i].brand.image
                        )
                    )
                    Log.e("eee",
                        brand_test(
                            it[i].brand.id,
                            it[i].brand.title,
                            it[i].brand.image
                        ).toString())
                }
            })
        }
    }

    private fun openChooseImage() {
        PickImageDialog.build(PickSetup().setTitle("Select Image").setSystemDialog(true))
            .setOnPickResult { onPickResult(it) }.setOnPickCancel {}.show(activity)
    }


    private fun showFilterDialog(){
        val bottom = BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.bottom_sheet_search)
            setCancelable(true)
        }
        bottom.btn_apply_submit.setOnClickListener {
            var choice=""
            if (bottom.radio_new.isChecked){
                choice = bottom.radio_new.text.toString()
            }else{
                choice = bottom.radio_used.text.toString()
            }
            if (selected_array.isNotEmpty() && bottom.btn_selected_type_list.text.toString() != "Selected Type" && choice !="") {
                bottom.dismiss()
                Move_to_all_list("",selected_array[0].title.toString(),bottom.btn_selected_type_list.text.toString(),choice)
                Toast.makeText(
                    requireContext(),
                    choice + " || " + selected_array[0].title.toString() + "||\n" + bottom.btn_selected_type_list.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.getselectedModel(selected_array[0].title.toString(),bottom.btn_selected_type_list.text.toString(),choice){}
            }

        }

        bottom.list_brand.adapter =adapter_selected
        bottom.btn_selected_type_list.setOnClickListener {
            if (selected_array.isNotEmpty()) {
            showMdoelDialod{dialog ->
                    viewModel.getModelData(selected_array[0].title.toString()) {
                        Log.e("eee model",it.toString())
                        dialog.model_list.adapter = model_type_Adapter(requireActivity(), it,object :model_type_Adapter.onClick{
                            override fun onClickItem(position: Int, type: Int) {
                                when(type){
                                    1->{
                                        bottom.btn_selected_type_list.text = it[position].title
                                        dialog.dismiss()
                                    }
                                }
                            }

                        })
                    }
            }
            }else{
                Toast.makeText(requireContext(),"Select Brand",Toast.LENGTH_SHORT).show()
            }
        }
        bottom.show()
    }


    private fun showMdoelDialod(onComplete:(dialog:BottomSheetDialog)->Unit){
        val bottom = BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.car_model_dialog)
            setCancelable(true)
        }
        bottom.show()
        onComplete(bottom)
    }






    private fun move_to_details(position: Int){
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(array[position])
        Navigation.findNavController(root).navigate(action)
    }

    override fun onPickResult(r: PickResult?) {
        if (r!!.error == null){
            file = File(r.path)
            val params = RequestParams().apply {
                put("image",file)
            }
            viewModel.post("https://mazira-pdf-to-png1.p.rapidapi.com/",params)
        }else{
        }
    }

    override fun onClick(position: Int, type: Int) {
                when(type){
                    1->{
                        if (selected_array.isEmpty()){
                            selected_array.add(brand_array_api[position])
                        }else{
                            selected_array.removeAt(0)
                            selected_array.add(brand_array_api[position])
                        }
                        adapter_selected.notifyDataSetChanged()
                    }
                }
    }


}
