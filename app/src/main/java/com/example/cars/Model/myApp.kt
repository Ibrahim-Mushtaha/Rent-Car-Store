package com.example.cars.Model

import android.app.Activity
import android.app.Dialog
import com.example.cars.R
import kotlinx.android.synthetic.main.loading_dialog.*

class myApp {
    companion object{
        lateinit var dialog : Dialog

         fun showLoadingDialog(activity: Activity){
            dialog = Dialog(activity).apply {
                setContentView(R.layout.loading_dialog)
                setCancelable(true)
            }
            dialog.avi.show()
            dialog.show()
        }


         fun hideLoadingDialog(){
            dialog.hide()
        }
    }
}