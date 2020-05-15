package id.dev.qurmer.intro.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class OnBoardFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val count = 3

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment = OnBoardOneFragment()
        when (position) {
            0 -> fragment = newInstanceFirst()
            1 -> fragment = newInstanceSecond()
            2 -> fragment = newInstanceThird()
        }
        return fragment
    }

    override fun getCount(): Int = count

    companion object {

        fun newInstanceFirst(): OnBoardOneFragment {
            val bindData = Bundle()
            val oneFragment = OnBoardOneFragment()
            oneFragment.arguments = bindData
            return oneFragment
        }

        fun newInstanceSecond(): OnBoardTwoFragment {
            val bindData = Bundle()
            val twoFragment = OnBoardTwoFragment()
            twoFragment.arguments = bindData
            return twoFragment
        }

        fun newInstanceThird(): OnBoardThreeFragment {
            val bindData = Bundle()
            val threeFragment = OnBoardThreeFragment()
            threeFragment.arguments = bindData
            return threeFragment
        }
    }
}