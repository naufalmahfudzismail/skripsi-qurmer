package id.dev.qurmer.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.UserResponse
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

        val adapter = ProfileFragmentAdapter(childFragmentManager, activity!!)
        viewPager_profile.adapter = adapter
        tabLayout_profile.setupWithViewPager(viewPager_profile)

        presenter = ProfilePresenter(context!!, this)
        presenter.getUser((activity as BaseActivity).getTokenWithBearer())

    }

    override fun onResult(result: UserResponse?) {
        activity?.runOnUiThread {
            if (result != null) {

                val data = result.data

                txt_name.text = data?.user?.nama
                txt_point.text = data?.score?.totalScore
                txt_rank.text = data?.user?.rank.toString()
                txt_work.text = data?.user?.pekerjaan

                val progress = result.data?.progress
                progress?.forEach {

                    val challenge = it.challenge
                    if (it.isDone == "1") {
                        txt_level.text = challenge?.levelId
                    } else {
                        txt_level.text = "0"
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
