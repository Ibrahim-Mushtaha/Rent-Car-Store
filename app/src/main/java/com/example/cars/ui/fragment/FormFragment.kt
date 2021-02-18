package com.example.cars.ui.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.cars.Model.User
import com.example.cars.R
import kotlinx.android.synthetic.main.form_fragment.view.*

/**
 * A simple [Fragment] subclass.
 */
class FormFragment : Fragment() {


    val args: FormFragmentArgs by navArgs()
    var obj = User()


    lateinit var root:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.form_fragment, container, false
        )
        root = binding.root

        if (args.checkUser.email != ""){
            root.etxt_user_name.setText(args.checkUser.name)
            root.etxt_phone_number.setText(args.checkUser.email)
            root.etxt_user_email.setText(args.checkUser.phone)
        }else{
            root.btn_next_step.setOnClickListener {
                nextStep()
            }
        }

        root.btn_next_step.setOnClickListener {
            nextStep()
        }

        return root
    }


    private fun nextStep(){
        val email = root.etxt_user_email.text.toString()
        if (root.etxt_user_name.text.isNotEmpty() && root.etxt_user_email.text.isNotEmpty() && root.etxt_phone_number.text.isNotEmpty()){
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                root.etxt_user_email.error = "Please enter a valid email"
                root.etxt_user_email.requestFocus()
            }else {

                obj = User(
                    root.etxt_user_name.text.toString(),
                    root.etxt_user_email.text.toString(),
                    root.etxt_phone_number.text.toString(),
                    root.city_spinner.selectedItem.toString(),
                    "",
                    ""
                )
                val action =
                    FormFragmentDirections.actionFormFragmentToGuaranteesFragment(
                        obj,
                        args.form!!
                    )
                //val action  =  FormFragmentDirections.actionFormFragmentToCheckoutFragment(obj,args.form!!)
                Navigation.findNavController(root).navigate(action)
            }
        }else{
            Toast.makeText(requireContext(),"Input is Empty",Toast.LENGTH_SHORT).show()
        }
    }

}
