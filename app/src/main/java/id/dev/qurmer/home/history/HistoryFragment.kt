package id.dev.qurmer.home.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.HistoryResponse
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment(), HistoryView {

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                }
            }
    }

    private lateinit var presenter: HistoryPresenter
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HistoryPresenter(context!!, this)
        presenter.getHistory((activity as BaseActivity).getTokenWithBearer())
    }

    override fun onResult(result: HistoryResponse?) {
        if (result?.data != null && result.data!!.isNotEmpty()) {
            adapter = HistoryAdapter(context!!, result.data!!)

            rv_history.layoutManager = LinearLayoutManager(context!!)
            rv_history.itemAnimator = DefaultItemAnimator()
            rv_history.adapter = adapter

        } else {
            ll_history_activity.visibility = View.GONE
            ll_empty_activity.visibility = View.VISIBLE
        }

    }

    override fun onEmpty() {
        ll_history_activity.visibility = View.GONE
        ll_empty_activity.visibility = View.VISIBLE
    }

    override fun startLoading() {
        (activity as BaseActivity).viewLoading()
    }

    override fun stopLoading() {
        (activity as BaseActivity).hideLoading()
    }

    override fun onUnAuthorized() {

    }
}