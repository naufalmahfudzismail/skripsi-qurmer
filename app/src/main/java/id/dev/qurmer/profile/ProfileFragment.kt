package id.dev.qurmer.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.config.DialogChallengeListener
import id.dev.qurmer.data.model.UserResponse
import id.dev.qurmer.intro.IntroActivity
import id.dev.qurmer.intro.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), ProfileView {

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                }
            }
    }


    private lateinit var presenter: ProfilePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProfilePresenter(context!!, this)
        presenter.getUser((activity as BaseActivity).getTokenWithBearer())

        btn_exit.setOnClickListener {
            (activity as BaseActivity).showDialogChallenge(
                "Keluar Akun",
                "Kamu yakin ingin keluar akun?",
                object : DialogChallengeListener {
                    override fun onPositiveClicked(dialog: Dialog) {
                        dialog.dismiss()
                        (activity as BaseActivity).logOut()
                        (activity as BaseActivity).startActivityClearPreviousActivity<IntroActivity>()

                    }
                    override fun onNegativeClicked(dialog: Dialog) {
                        dialog.dismiss()
                    }

                })
        }
    }

    override fun onResult(result: UserResponse?) {
        activity?.runOnUiThread {
            if (result != null) {
                val adapter = ProfileFragmentAdapter(childFragmentManager, activity!!, result)
                viewPager_profile.adapter = adapter
                tabLayout_profile.setupWithViewPager(viewPager_profile)

                val data = result.data

                txt_name.text = data?.user?.nama
                txt_point.text = data?.score?.totalScore
                txt_rank.text = data?.user?.rank.toString()
                txt_work.text = data?.user?.pekerjaan

                val progress = result.data?.progress

                if (progress!!.isEmpty()) txt_level.text = "1"
                else {
                    val lvl1 = progress.filter { it.level == "1" }
                    val lvl2 = progress.filter { it.level == "2" }
                    when {
                        lvl1.size == 6 -> txt_level.text = "2"
                        lvl2.size == 9 -> txt_level.text = "3"
                        else -> {
                            progress.forEach {
                                txt_level.text = it.level
                            }
                        }
                    }
                }
            }
        }
    }

    override fun startLoading() {
        (activity as BaseActivity).viewLoading()
    }

    override fun stopLoading() {
        activity?.runOnUiThread {
            (activity as BaseActivity).hideLoading()
        }
    }

    override fun onUnAuthorized() {

    }


}
