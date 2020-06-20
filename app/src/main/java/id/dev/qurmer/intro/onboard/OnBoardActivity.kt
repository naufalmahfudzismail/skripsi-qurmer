package id.dev.qurmer.intro.onboard

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import id.dev.qurmer.MainActivity
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.config.SessionManager
import id.dev.qurmer.intro.IntroActivity
import kotlinx.android.synthetic.main.activity_on_board.*

class OnBoardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_on_board)


        val introFragmentAdapter = OnBoardFragmentAdapter(supportFragmentManager)
        fragment_on_board_pager.adapter = introFragmentAdapter
        tabDots.setupWithViewPager(fragment_on_board_pager, true)

        btn_next.setOnClickListener {

            if (fragment_on_board_pager.currentItem == 2) {
                SessionManager.getInstance(this).setIntro()
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            } else {
                fragment_on_board_pager.currentItem = fragment_on_board_pager.currentItem + 1
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (getIntroStatus()) {
            val intent = Intent(this, IntroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.finish()
        }

    }


}
