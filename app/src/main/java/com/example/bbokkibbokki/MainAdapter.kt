package com.example.bbokkibbokki

import Fragment.AFragment
import Fragment.BFragment
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val fragmentTitleList = mutableListOf("A","B")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AFragment()
            1 -> BFragment()
            else -> {throw IllegalStateException("$position is illegal") }
        }

    }
    //생성할 Fragment 개수
    override  fun getCount() = 2

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
    }

    //getPageTitle 로 탭레이아웃 전환에 따른 포지션값 받아 처리가능
    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }
}