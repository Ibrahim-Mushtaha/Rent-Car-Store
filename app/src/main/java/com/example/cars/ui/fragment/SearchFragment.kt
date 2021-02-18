package com.example.cars.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.cars.Model.CarNewElement
import com.example.cars.R
import com.example.cars.adapter.AllCarAdapter
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.search_fragment.view.*


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), AllCarAdapter.onClick {


    var array = ArrayList<CarNewElement>()

    private val adapter by lazy {
        AllCarAdapter(
            requireActivity(),
            array,
            this,
            "all"
        )
    }

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.search_fragment, container, false
        )
        root = binding.root


        root.search_list.adapter=adapter


        root.btn_check.setOnClickListener {
            IntentIntegrator(requireActivity()).initiateScan()

        }

        root.txt_search.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            adapter.filter.filter(s)
            }

        })

        return root
    }

     override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClickItem(position: Int, type: Int) {

    }

}
