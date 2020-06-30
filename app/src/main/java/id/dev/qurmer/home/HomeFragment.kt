package id.dev.qurmer.home

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import id.dev.qurmer.R
import id.dev.qurmer.challenge.ChallengeActivity
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.ChallengeDataResponse
import id.dev.qurmer.data.model.QuoteDataResponse
import id.dev.qurmer.home.challenge.DailyChallengeActivity
import id.dev.qurmer.media.list.ListSurahActivity
import id.dev.qurmer.rank.LeaderBoardActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment(), HomeView {


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


        val presenter = HomePresenter(context!!, this)
        presenter.getQuote((activity as BaseActivity).getTokenWithBearer())

        greeting()

        btn_menu_audio.setOnClickListener {
            (activity as BaseActivity).startActivityWithIntent<ListSurahActivity>("type" to 1)
        }

        btn_memorize.setOnClickListener {
            (activity as BaseActivity).startActivityWithIntent<ListSurahActivity>("type" to 2)
        }

        ll_challenge.setOnClickListener {
            (activity as BaseActivity).startActivityWithIntent<ChallengeActivity>()
        }

        ll_rank.setOnClickListener {
            (activity as BaseActivity).startActivityWithIntent<LeaderBoardActivity>()
        }

        btn_start_daily.setOnClickListener {
            (activity as BaseActivity).startActivityWithIntent<DailyChallengeActivity>()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onQuoteResult(result: QuoteDataResponse?) {
        if (result != null) {

            val adapter = result.data?.quotes?.let { QuoteAdapter(activity!!, it) }
            rv_quotes.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rv_quotes.itemAnimator = DefaultItemAnimator()
            rv_quotes.adapter = adapter

            txt_name_home.text = "Halo, ${result.data?.user?.nama}"


        }
    }

    override fun onChallengeDaily(result: ChallengeDataResponse?) {

    }

    override fun startLoading() {
        (activity as BaseActivity).viewLoading()
    }

    override fun stopLoading() {
        (activity as BaseActivity).hideLoading()
    }

    override fun onUnAuthorized() {

    }

    private fun greeting() {
        val calendar = Calendar.getInstance()
        when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> {
               bg_home.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.bg_header_home_recitation))
            }
            in 12..17 -> {
                bg_home.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.bg_header_home_masjid_away))
            }
            in 18..23 -> {
                bg_home.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.bg_header_home_masjid))
            }
        }
    }


    private fun requestMultiplePermissions() {
        if (activity!!.isFinishing) return
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.deniedPermissionResponses.size != 0) {
                        (activity as BaseActivity).makeLongToast("Semua Perizinan Harus di Izinkan")
                        //startActivity(intent)
                    }

                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        val background = (object : Thread() {
                            override fun run() {
                                try {
                                    sleep(5000)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }).apply {
                            start()
                        }
                    }
                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {

                    }

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { (activity as BaseActivity).makeLongToast("Error") }
            .onSameThread()
            .check()
    }


}
