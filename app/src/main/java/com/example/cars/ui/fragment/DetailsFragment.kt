package com.example.cars.ui.fragment

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.example.cars.Model.User
import com.example.cars.R
import com.example.cars.adapter.image_slider.ImageList_Adapter
import com.example.cars.other.*
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.details_fragment.view.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment(), IPickResult {




    val args: DetailsFragmentArgs by navArgs()
    val array = ArrayList<String>()

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.details_fragment, container, false
        )
        root = binding.root
        root.apply {
            if (args.NewCarElement.title == "") {
                txt_title.text = "Car"
            } else {
                txt_title.text = args.NewCarElement.title
            }
            if (args.NewCarElement.price != null) {
                txt_price.text = args.NewCarElement.price
                txt_brand.text = args.NewCarElement.brand.title + " " + args.NewCarElement.year
                if (args.NewCarElement.mileage.toInt() > 250) {
                    txt_mileage_d.visibility = View.VISIBLE
                    txt_mileage_d.text = args.NewCarElement.mileage + " km"
                } else {
                    txt_mileage_d.visibility = View.GONE
                }
            }


            Log.e("eee sub", args.NewCarElement.additional_features)

            AndroidNetworking.initialize(requireContext())

            btn_download.setOnClickListener {


                InsertToStorage()


            }
        }
        root.btn_buy.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToFormFragment3(args.NewCarElement,
                User("","","","","","")
            )
            Navigation.findNavController(root).navigate(action)
        }

        setUpViewpager()

        return root
    }



    private fun InsertToStorage(){
        val rooot = Environment.getExternalStorageDirectory().toString()
        val mydir = File(rooot + "/saved_image")
        if (!mydir.exists()){
            mydir.mkdir()
        }

        val fname = "Image_"+args.NewCarElement.title+"/jpg"
        val file = File(mydir,fname)
        if (file.exists())
            file.delete()
         try {
        val out = FileOutputStream(file);
        out.flush();
        out.close();

                Toast.makeText(requireContext(),"file created",Toast.LENGTH_SHORT).show()
    } catch (e:Exception) {
             Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_SHORT).show()
             Log.e("eee error",e.message.toString())
         e.printStackTrace();
    }
    }


    private fun setUpViewpager() {
        if (args.NewCarElement.images.isNotEmpty()) {
            root.view_pager.adapter =
                ImageList_Adapter(
                    requireActivity(),
                    args.NewCarElement.images
                )
        } else {
            array.add(args.NewCarElement.main_image)
            root.view_pager.adapter =
                ImageList_Adapter(
                    requireActivity(),
                    array
                )
        }
        root.view_pager.clipToPadding = false
        root.view_pager.clipChildren = false
        root.view_pager.setOffscreenPageLimit(3)
        root.view_pager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER)

        root.indicator.setViewPager2(root.view_pager)
    }

    override fun onPickResult(r: PickResult?) {
        if (r!!.error == null) {
            Log.e("$TAG image", r.path.toString())
        } else {
            Log.e("$TAG image", "null")
        }
    }

}
