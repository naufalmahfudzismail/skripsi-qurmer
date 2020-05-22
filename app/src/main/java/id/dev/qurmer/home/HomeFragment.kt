package id.dev.qurmer.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.media.list.ListSurahActivity
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_menu_audio.setOnClickListener {
            (activity as BaseActivity).startActivityWithIntent<ListSurahActivity>()
        }
    }


}
