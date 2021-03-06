package id.dev.qurmer

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.GlobalData.FRAGMENT_STACK
import id.dev.qurmer.data.database.hash.HashViewModel
import id.dev.qurmer.home.HomeFragment
import id.dev.qurmer.home.history.HistoryFragment
import id.dev.qurmer.profile.ProfileFragment
import id.dev.qurmer.utils.fingerprint.operation.OperationHash
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_nav_selected)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        //val hashViewModel = ViewModelProviders.of(this).get(HashViewModel::class.java)


        nav_home.setOnNavigationItemSelectedListener {
            controlNavbarStack(it.itemId)
            //loadFragment(it.itemId)

            when (it.itemId) {
                R.id.profile_main -> {
                    loadProfileFragment(savedInstanceState)
                }
                R.id.notification_main -> {
                    loadHistoryFragment(savedInstanceState)
                }
                R.id.home_main -> {
                    loadHomeFragment(savedInstanceState)

                }
            }

            true
        }

        nav_home.selectedItemId = R.id.home_main
        //DataStore.NAVIGATION_HISTORY.add(R.id.home_main)


    }


    private fun loadHomeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    HomeFragment(),
                    HomeFragment::class.java.simpleName
                )
                .commitAllowingStateLoss()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    HomeFragment(),
                    HomeFragment::class.java.simpleName
                ).commit()
        }
    }

    private fun loadProfileFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ProfileFragment(),
                    ProfileFragment::class.java.simpleName
                )
                .commitAllowingStateLoss()
        }
    }

    private fun loadHistoryFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    HistoryFragment(),
                    HistoryFragment::class.java.simpleName
                )
                .commitAllowingStateLoss()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    HistoryFragment(),
                    HistoryFragment::class.java.simpleName
                ).commit()
        }
    }

   /* private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.home_main -> {
                HomeFragment.newInstance()
            }

            R.id.notification_main -> {
                HistoryFragment.newInstance()
            }
            R.id.profile_main -> {
                ProfileFragment.newInstance()
            }
            else -> {
                null
            }
        }

        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()

            if (viewModel.lastActiveFragmentTag != null) {
                val lastFragment =
                    supportFragmentManager.findFragmentByTag(viewModel.lastActiveFragmentTag)
                if (lastFragment != null)
                    transaction.hide(lastFragment)
            }

            if (!fragment.isAdded) {
                transaction.add(R.id.fragment_container, fragment, tag)
            } else {
                transaction.show(fragment)
            }

            transaction.commit()
            viewModel.lastActiveFragmentTag = tag
        }
    }*/

    private fun controlNavbarStack(itemId: Int) {
        val isStackedBefore = FRAGMENT_STACK.contains(itemId)
        if (isStackedBefore) FRAGMENT_STACK.remove(itemId)
        FRAGMENT_STACK.add(itemId)
    }

    override fun onBackPressed() {
        when {
            FRAGMENT_STACK.size > 1 -> {
                val lastStackInit = FRAGMENT_STACK.lastIndex - 1
                nav_home.selectedItemId = FRAGMENT_STACK[lastStackInit]
                repeat(2) {
                    FRAGMENT_STACK.removeAt(
                        FRAGMENT_STACK.lastIndex
                    )
                }
            }
            FRAGMENT_STACK.size == 1 -> {
                nav_home.selectedItemId = FRAGMENT_STACK[0]
                FRAGMENT_STACK.removeAt(0)
            }
            else -> {
                finishAndRemoveTask()
            }
        }
    }
}
