package id.dev.qurmer.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity

class TaskActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
    }
}