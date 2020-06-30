package id.dev.qurmer.challenge

import android.os.Bundle
import androidx.core.content.ContextCompat
import id.dev.qurmer.MainActivity
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.ChallengeResponse
import kotlinx.android.synthetic.main.activity_challenge.*

class ChallengeActivity : BaseActivity(), ChallengeView {


    private lateinit var presenter: ChallengePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        presenter = ChallengePresenter(this, this)
        presenter.getStaticChallenge(getTokenWithBearer())

    }

    override fun onResult(result: ChallengeResponse?) {

        if (result?.error == false) {

            val data = result.data

            val levelOne = data?.challenge?.filter { it.level?.level == "1" }
            val levelTwo = data?.challenge?.filter { it.level?.level == "2" }
            val levelThree = data?.challenge?.filter { it.level?.level == "3" }

            val progress = data?.progress
            val progressLevelOne = progress?.filter { it.level == "1" }
            val progressLevelTwo = progress?.filter { it.level == "2" }
            val progressLevelThree = progress?.filter { it.level == "3" }

            var currentLevel: Int? = null
            var currentPart: Int? = null


            // level 1
            if (progressLevelOne!!.size >= 3) {
                ll_one_one.setOnClickListener {
                    makeToast("Anda Sudah menyelesaikan Level Ini")
                }

                img_level_one_two.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_challenge_level_one
                    )
                )
                when {
                    progressLevelOne.size < 6 -> {
                        currentLevel = 1
                        currentPart = 2
                        ll_one_two.setOnClickListener {
                            startActivityWithIntent<ListChallengeActivity>("data" to data, "level" to 1)
                        }
                    }
                    else -> {
                        ll_one_two.setOnClickListener {
                            makeToast("Anda Sudah menyelesaikan Level Ini")
                        }
                    }
                }

            } else {
                currentLevel = 1
                currentPart = 1
                ll_one_one.setOnClickListener {
                    startActivityWithIntent<ListChallengeActivity>("data" to data, "level" to 1)
                }

                ll_one_two.setOnClickListener {
                    makeToast("Anda Harus menyelesaikan tantangan level 1 bagian 1")
                }
            }


            //level 2
            if (progressLevelOne.size >= 6) {
                img_level_two_one.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_challenge_level_two
                    )
                )
                if (progressLevelTwo!!.size >= 3) {
                    ll_two_one.setOnClickListener { makeToast("Anda Sudah menyelesaikan Level Ini") }
                    img_level_two_two.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_challenge_level_two
                        )
                    )
                    if (progressLevelTwo.size >= 6) {
                        ll_two_two.setOnClickListener { makeToast("Anda Sudah menyelesaikan Level Ini") }
                        img_level_two_three.setImageDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_challenge_level_two
                            )
                        )
                        if (progressLevelTwo.size == 9) {
                            ll_two_three.setOnClickListener { makeToast("Anda Sudah menyelesaikan Level Ini") }
                        } else {
                            currentLevel = 2
                            currentPart = 3
                            ll_two_three.setOnClickListener {
                                startActivityWithIntent<ListChallengeActivity>(
                                    "data" to data, "level" to 2
                                )
                            }
                        }
                    } else {
                        currentLevel = 2
                        currentPart = 2
                        ll_two_two.setOnClickListener {
                            startActivityWithIntent<ListChallengeActivity>(
                                "data" to data, "level" to 2
                            )
                        }
                        ll_two_three.setOnClickListener {
                            makeToast("Anda Harus menyelesaikan semua Tantangan Level 2 bagian 2")
                        }
                    }
                } else {
                    currentLevel = 2
                    currentPart = 1
                    ll_two_one.setOnClickListener {
                        startActivityWithIntent<ListChallengeActivity>(
                            "data" to data, "level" to 2
                        )
                    }

                    ll_two_three.setOnClickListener {
                        makeToast("Anda Harus menyelesaikan semua Tantangan Level 2 bagian 1")
                    }

                    ll_two_two.setOnClickListener {
                        makeToast("Anda Harus menyelesaikan semua Tantangan Level 2 bagian 1")
                    }
                }
            } else {

                ll_two_one.setOnClickListener {
                    makeToast("Anda Harus menyelesaikan semua Tantangan Level 1")
                }

                ll_two_three.setOnClickListener {
                    makeToast("Anda Harus menyelesaikan semua Tantangan Level 1")
                }

                ll_two_two.setOnClickListener {
                    makeToast("Anda Harus menyelesaikan semua Tantangan Level 1")
                }
            }


            //level 3
            if (progressLevelTwo!!.size >= 9) {

                img_level_three_one.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_challenge_level_three
                    )
                )
                if (progressLevelThree!!.size >= 3) {
                    ll_three_one.setOnClickListener { makeToast("Anda Sudah menyelesaikan Level Ini") }
                    img_level_three_two.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_challenge_level_three
                        )
                    )
                    if (progressLevelThree.size >= 6) {
                        ll_three_two.setOnClickListener { makeToast("Anda Sudah menyelesaikan Level Ini") }
                        img_level_three_three.setImageDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_challenge_level_three
                            )
                        )
                        if (progressLevelThree.size == 9) {
                            ll_three_three.setOnClickListener { makeToast("Anda Sudah menyelesaikan Level Ini") }
                        } else {
                            currentLevel = 3
                            currentPart = 3
                            ll_three_three.setOnClickListener {
                                startActivityWithIntent<ListChallengeActivity>(
                                    "data" to data, "level" to 3
                                )
                            }
                        }
                    } else {
                        currentLevel = 3
                        currentPart = 2
                        ll_three_two.setOnClickListener {
                            startActivityWithIntent<ListChallengeActivity>(
                                "data" to data, "level" to 3
                            )
                        }
                        ll_three_three.setOnClickListener {
                            makeToast("Anda Harus menyelesaikan tantangan level 3 bagian 2")
                        }
                    }
                } else {


                    currentLevel = 3
                    currentPart = 1
                    ll_three_one.setOnClickListener {
                        startActivityWithIntent<ListChallengeActivity>(
                            "data" to data, "level" to 3
                        )
                    }
                    ll_three_three.setOnClickListener {
                        makeToast("Anda Harus menyelesaikan tantangan level 3 bagian 1 dan bagian 2")
                    }

                    ll_three_two.setOnClickListener {
                        makeToast("Anda Harus menyelesaikan tantangan level 3 bagian 1")
                    }
                }
            } else {

                ll_three_one.setOnClickListener {
                    makeToast("Anda Harus menyelesaikan semua Tantangan Level 2")
                }

                ll_three_three.setOnClickListener {
                    makeToast("Anda Harus menyelesaikan semua Tantangan Level 2")
                }

                ll_three_two.setOnClickListener {
                    makeToast("Anda Harus menyelesaikan semua Tantangan Level 2")
                }
            }

            txt_level_challenge.text = currentLevel.toString()
            txt_part_challenge.text = currentPart.toString()


        }

    }

    override fun startLoading() {
        viewLoading()
    }

    override fun stopLoading() {
        hideLoading()
    }

    override fun onUnAuthorized() {

    }

    override fun onBackPressed() {
        startActivityClearPreviousActivity<MainActivity>()
    }
}
