package com.example.cars.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cars.Model.Boarding
import com.example.cars.R
import com.example.cars.adapter.intro.onBoarding_Adapter
import com.example.cars.other.*
import kotlinx.android.synthetic.main.intro_activity.*

class IntroActivity : AppCompatActivity(), onBoarding_Adapter.onClick {


    override fun onStart() {
        if (restorePref()){
            MovetoMainActivity()
        }
        super.onStart()
    }
    val array = ArrayList<Boarding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_activity)

        setUpViewpager()
    }


    private fun setUpViewpager() {
        val array = arrayOf(
            Boarding(getString(R.string.long_string), R.drawable.img1),
            Boarding(getString(R.string.long_string), R.drawable.img1),
            Boarding(getString(R.string.long_string), R.drawable.img1)
        )

        view_pager_intro.adapter = onBoarding_Adapter(this, array, this)
        view_pager_intro.clipToPadding = false
        view_pager_intro.clipChildren = false
        view_pager_intro.setOffscreenPageLimit(3)
        view_pager_intro.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER)

        view_pager_intro.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2){
                    indicator_boarding_intro.visibility= View.GONE
                }else{
                    indicator_boarding_intro.visibility= View.VISIBLE
                }
            }
        })

        indicator_boarding_intro.setViewPager2(view_pager_intro)
    }

    override fun onClickItem(position: Int, type: Int) {
        when(type){
            1->{
                MovetoMainActivity()
            }
        }
    }


    private fun MovetoMainActivity(){
        val intent= Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent).also {
            SavePrefsData()
        }
    }

    private fun restorePref(): Boolean {
        val Pref =getSharedPreferences(INTRO, Context.MODE_PRIVATE)
        val IsIntroActivityOpenedBefor =Pref.getBoolean(ISINTROOPEN,false)
        return IsIntroActivityOpenedBefor
    }

    private fun SavePrefsData() {
        val Pref = getSharedPreferences(INTRO, Context.MODE_PRIVATE)
        val editor = Pref.edit()
        editor.putBoolean(ISINTROOPEN,true)
        editor.commit()
    }

}
