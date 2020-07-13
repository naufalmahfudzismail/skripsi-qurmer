package id.dev.qurmer.challenge

import android.os.Bundle
import androidx.core.content.ContextCompat
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.ChallengeResponse
import kotlinx.android.synthetic.main.activity_list_challenge.*

class ListChallengeActivity : BaseActivity() {

    private var listChallenge: MutableList<ChallengeResponse.Data.Challenge> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_challenge)

        val data = intent.getSerializableExtra("data") as ChallengeResponse.Data
        val level = intent.getIntExtra("level", 1)

        val progress = data.progress
        val progressLevelOne = progress?.filter { it.level == "1" }
        val progressLevelTwo = progress?.filter { it.level == "2" }
        val progressLevelThree = progress?.filter { it.level == "3" }

        // Case nya bagian yang sudah tidak tahu surat nya apa game nya apa kaerena random, hanya membuka bagian yang belum

        when (level) {
            1 -> {
                val levelOne = data.challenge?.filter { it.level?.level == "1" }
                levelOne?.let { listChallenge.addAll(it) }
                val chooseList = listChallenge.shuffled()


                when {
                    progressLevelOne!!.size == 1 || progressLevelOne.size == 4 -> {
                        ll_part_one.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_one.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 1")
                        }

                        ll_part_two.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_rect_challenge_done)
                        ll_part_two.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[1],
                                "level" to level,
                                "static" to true
                            )
                        }

                        ll_part_three.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 2")
                        }

                    }
                    progressLevelOne.size == 2 || progressLevelOne.size == 5 -> {

                        ll_part_one.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_one.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 1")
                        }

                        ll_part_two.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_two.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 2")
                        }

                        ll_part_three.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_rect_challenge_done)
                        ll_part_three.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[3],
                                "level" to level,
                                "static" to true
                            )
                        }

                    }
                    else -> {

                        ll_part_one.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[0],
                                "level" to level,
                                "static" to true
                            )
                        }
                        ll_part_two.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 1")
                        }
                        ll_part_three.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 1 dan 2")
                        }
                    }
                }

            }
            2 -> {
                val levelTwo = data.challenge?.filter { it.level?.level == "2" }
                levelTwo?.let { listChallenge.addAll(it) }
                val chooseList = listChallenge.shuffled()

                when {
                    progressLevelTwo!!.size == 1 || progressLevelTwo.size == 4 || progressLevelTwo.size == 7 -> {

                        ll_part_one.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_one.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 1")
                        }

                        ll_part_two.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_rect_challenge_done)
                        ll_part_two.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[1],
                                "level" to level,
                                "static" to true
                            )
                        }

                        ll_part_three.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 2")
                        }

                    }
                    progressLevelTwo.size == 2 || progressLevelTwo.size == 5 || progressLevelTwo.size == 8 -> {

                        ll_part_one.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_one.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 1")
                        }

                        ll_part_two.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_two.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 2")
                        }

                        ll_part_three.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_rect_challenge_done)
                        ll_part_three.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[3],
                                "level" to level,
                                "static" to true
                            )
                        }

                    }
                    else -> {
                        ll_part_one.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[0],
                                "level" to level,
                                "static" to true
                            )
                        }
                        ll_part_two.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 1")
                        }
                        ll_part_three.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 1 dan 2")
                        }
                    }
                }
            }
            3 -> {
                val levelThree = data.challenge?.filter { it.level?.level == "3" }
                levelThree?.let { listChallenge.addAll(it) }
                val chooseList = listChallenge.shuffled()

                when {
                    progressLevelThree!!.size == 1 || progressLevelThree.size == 4 || progressLevelThree.size == 7 -> {

                        ll_part_one.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_one.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 1")
                        }

                        ll_part_two.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_rect_challenge_done)
                        ll_part_two.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[1],
                                "level" to level,
                                "static" to true
                            )
                        }

                        ll_part_three.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 2")
                        }

                    }
                    progressLevelThree.size == 2 || progressLevelThree.size == 5 || progressLevelThree.size == 8 -> {

                        ll_part_one.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_one.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 1")
                        }

                        ll_part_two.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_bg_theme_challenge)
                        ll_part_two.setOnClickListener {
                            makeToast("Selamat!, Anda sudah menyelesaikan bagian 2")
                        }

                        ll_part_three.background =
                            ContextCompat.getDrawable(this, R.drawable.ic_rect_challenge_done)
                        ll_part_three.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[3],
                                "level" to level,
                                "static" to true
                            )
                        }

                    }
                    else -> {
                        ll_part_one.setOnClickListener {
                            startActivityClearPreviousActivity<OverviewChallengeActivity>(
                                "data" to chooseList[0],
                                "level" to level,
                                "static" to true
                            )
                        }
                        ll_part_two.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 1")
                        }
                        ll_part_three.setOnClickListener {
                            makeToast("Anda Harus Menyelesaikan Bagian 1 dan 2")
                        }
                    }
                }
            }

        }
    }


}
