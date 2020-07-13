package id.dev.qurmer.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.dev.qurmer.R
import id.dev.qurmer.data.model.UserResponse
import kotlinx.android.synthetic.main.fragment_profile.txt_level
import kotlinx.android.synthetic.main.pencapaian_profile_fragment.*

class PencapaianFragment(val data: UserResponse) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pencapaian_profile_fragment, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daily = data.data?.daily
        val progress = data.data?.progress
        var level = ""

        if (progress!!.isEmpty()) level = "1"
        else {
            val lvl1 = progress.filter { it.level == "1" }
            val lvl2 = progress.filter { it.level == "2" }
            when {
                lvl1.size == 6 -> level = "2"
                lvl2.size == 9 -> level = "3"
                else -> {
                    progress.forEach {
                        level = it.level.toString()
                    }
                }
            }
        }

        when (level) {
            "1" -> {
                txt_judge.text = "Pemula"
            }
            "2" -> {
                txt_judge.text = "Menegah"
            }
            else -> {
                txt_judge.text = "Mahir"
            }
        }

        main_progress.progress = progress.size
        txt_progress.text = progress.size.toString()

        txt_level.text = "Level $level"
        txt_daily_level.text = "Level $level"
        txt_daily_progress.text = "Level $level"
        daily_progress.progress = daily!!.size
        txt_daily_progress.text = daily.size.toString()


    }
}