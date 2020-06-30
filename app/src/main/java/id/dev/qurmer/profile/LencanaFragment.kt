package id.dev.qurmer.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import id.dev.qurmer.R
import id.dev.qurmer.data.model.UserResponse
import kotlinx.android.synthetic.main.lencana_profile_fragment.*

class LencanaFragment(val data: UserResponse) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lencana_profile_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progress = data.data?.progress

        val lvl1 = progress?.filter { it.level == "1" }
        val lvl2 = progress?.filter { it.level == "2" }
        val lvl3 = progress?.filter { it.level == "3" }

        Log.e(
            "SIZE",
            lvl1?.size.toString() + ", " + lvl2?.size.toString() + ", " + lvl3?.size.toString()
        )

        if (lvl1?.size!! >= 3) {
            img_one_one.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.badge_one_one_done
                )
            )
            if (lvl1.size == 6) {
                img_one_two.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.badge_one_two_done
                    )
                )
            }
        }

        if (lvl2?.size!! >= 3) {
            img_two_one.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.badge_two_one_done
                )
            )
            if (lvl2.size >= 6) {
                img_two_two.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.badge_two_two_done
                    )
                )
                if (lvl2.size == 9) {
                    img_two_three.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.badge_two_three_done
                        )
                    )
                }
            }
        }

        if (lvl3?.size!! >= 3) {
            img_three_one.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.badge_three_one_done
                )
            )
            if (lvl3.size >= 6) {
                img_three_two.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.badge_three_two_done
                    )
                )
                if (lvl3.size == 3) {
                    img_three_three.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.badge_three_three_done
                        )
                    )
                }
            }
        }

    }
}