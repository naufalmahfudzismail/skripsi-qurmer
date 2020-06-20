package id.dev.qurmer.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ProfileFragmentAdapter(fm: FragmentManager, val context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment = LencanaFragment()
        when (position) {
            0 -> fragment = newInstanceLencana()
            1 -> fragment = newInstancePencapaian()
        }
        return fragment
    }

    override fun getCount(): Int = 2
    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            "Lencana"
        } else {
            "Pencapaian"
        }
    }

    companion object {

        fun newInstanceLencana(): LencanaFragment {
            val bindData = Bundle()
            val bookedFragment = LencanaFragment()
            bookedFragment.arguments = bindData
            return bookedFragment
        }

        fun newInstancePencapaian(): PencapaianFragment {
            val bindData = Bundle()
            val pendingFragment = PencapaianFragment()
            pendingFragment.arguments = bindData
            return pendingFragment
        }
    }


}