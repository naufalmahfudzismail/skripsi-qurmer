package id.dev.qurmer.intro.register

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.GlobalData.days
import id.dev.qurmer.data.GlobalData.months
import id.dev.qurmer.data.GlobalData.years
import id.dev.qurmer.data.model.CheckUsernameResponse
import id.dev.qurmer.data.model.RegisterDataResponse
import id.dev.qurmer.intro.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), RegisterView {


    private lateinit var registerPresenter: RegisterPresenter
    private var idMonth: Int? = null
    private var usernameIsUsed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerPresenter = RegisterPresenter(this, this)
        init()

    }

    private fun init() {

        val days = days()
        val years = years()
        val month = months.map { it.name }

        txt_pick_day.setOnClickListener { view ->
            showPopUpSpinner(days, view, object :
                ChooseItemSpinnerListener {
                override fun onChoose(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    txt_pick_day.text = days[position].toString()
                }
            })
        }

        edt_username.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && edt_username.text.toString().trim().isNotEmpty()) {
                registerPresenter.checkUserName(edt_username.text.toString())
            }
        }

        txt_pick_month.setOnClickListener { view ->
            showPopUpSpinner(month, view, object :
                ChooseItemSpinnerListener {
                override fun onChoose(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    txt_pick_month.text = month[position]
                    idMonth = months[position].id
                }
            })
        }

        txt_pick_year.setOnClickListener { view ->
            showPopUpSpinner(years, view, object :
                ChooseItemSpinnerListener {
                override fun onChoose(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    txt_pick_year.text = years[position].toString()
                }
            })
        }


        btn_register.setOnClickListener {
            onValidateRegister()
        }

    }

    private fun onValidateRegister() {

        var isThrough = true
        val name = edt_nama.text.toString()
        val pass = edt_password.text.toString()
        val username = edt_username.text.toString()
        val year = txt_pick_day.text.toString()
        val day = txt_pick_day.text.toString()
        val month = txt_pick_day.text.toString()
        val email = edt_email.text.toString()
        val gender = findViewById<RadioButton>(rg_gender.checkedRadioButtonId).text.toString()
        val work = edt_work.text.toString()

        val listField = arrayOf(name, pass, username, year, day, month, email, gender)
        listField.forEach {
            if (it.trim().isEmpty()) isThrough = false
        }


        if (isThrough && !usernameIsUsed) {
            val date = "$year-$idMonth-$day"
            registerPresenter.register(
                email = email,
                name = name,
                password = pass,
                date = date,
                username = username,
                gender = gender,
                work = work
            )
        } else {
            makeLongToast("Semua isian harus di isi!")
        }

    }

    override fun onResult(result: RegisterDataResponse?) {
        if (result?.error == false) {
            val data = result.data
            makeToast("Silahkan login")
            startActivityClearPreviousActivity<LoginActivity>("email" to data?.email)
        } else {
            makeToast("Terjadi Kesalahan")
        }
    }

    override fun onResultCheck(result: CheckUsernameResponse?) {
        usernameIsUsed = if (result?.data?.terdaftar!!) {
            makeToast("Username sudah di pakai")
            true
        } else {
            false
        }
    }

    override fun startLoading() {
        viewLoading()
    }

    override fun stopLoading() {
        hideLoading()
    }

    override fun onError(error: String) {
       makeLongToast(error)
    }

    override fun onUnAuthorized() {

    }

    private fun showPopUpSpinner(
        data: List<*>,
        v: View,
        listener: ChooseItemSpinnerListener,
        height: Int = 500
    ) {

        val popUpWindow = PopupWindow(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, data)

        val listView = ListView(this)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            listener.onChoose(parent, view, position, id)
            popUpWindow.dismiss()
        }

        popUpWindow.isFocusable = true
        popUpWindow.width = v.width
        popUpWindow.height = height
        popUpWindow.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.rounded_white
            )
        )
        popUpWindow.contentView = listView

        popUpWindow.showAsDropDown(v, 0, 0)
    }
}
