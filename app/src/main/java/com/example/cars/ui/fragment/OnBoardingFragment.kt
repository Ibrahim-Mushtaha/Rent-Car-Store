package com.example.cars.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cars.Model.Boarding
import com.example.cars.R
import com.example.cars.adapter.intro.onBoarding_Adapter
import com.example.cars.other.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.on_boarding_fragment.*
import kotlinx.android.synthetic.main.on_boarding_fragment.view.*

/**
 * A simple [Fragment] subclass.
 */
class OnBoardingFragment : Fragment() ,
    onBoarding_Adapter.onClick{


    override fun onStart() {
        if (restorePref()){
            Navigation.findNavController(root).navigate(R.id.action_onBoarding_Fragment_to_home_Fragment)
        }
        super.onStart()
    }

    val array = ArrayList<Boarding>()
    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().toolbar.visibility=View.GONE
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.on_boarding_fragment, container, false
        )
        root = binding.root


        setUpViewpager()
        return root
    }


    private fun setUpViewpager() {
        array.add(Boarding(getString(R.string.onboarding_description),
            R.drawable.img1
        ))
            array.add(Boarding(getString(R.string.onboarding_description),
                R.drawable.img2
            ))
            array.add(Boarding(getString(R.string.onboarding_description),
                R.drawable.img3
            ))

        root.view_pager2.clipToPadding = false
        root.view_pager2.clipChildren = false
        root.view_pager2.offscreenPageLimit = 3
        root.view_pager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER)

        root.view_pager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
               if (position == 2){
                   requireActivity().indicator_boarding.visibility=View.GONE
               }else{
                   requireActivity().indicator_boarding.visibility=View.VISIBLE
               }
            }
        })

        root.indicator_boarding.setViewPager2(root.view_pager2)
    }

    override fun onClickItem(position: Int, type: Int) {
        when(type){
            1->{
                SavePrefsData()
                Navigation.findNavController(root).navigate(R.id.action_onBoarding_Fragment_to_home_Fragment)
            }
        }
    }


    private fun restorePref(): Boolean {
        val Pref = requireActivity().getSharedPreferences(INTRO, Context.MODE_PRIVATE)
        val IsIntroActivityOpenedBefor =Pref.getBoolean(ISINTROOPEN,false)
        return IsIntroActivityOpenedBefor
    }

    private fun SavePrefsData() {
        val Pref = requireActivity().getSharedPreferences(INTRO, Context.MODE_PRIVATE)
        val editor = Pref.edit()
        editor.putBoolean(ISINTROOPEN,true)
        editor.commit()
    }

}
