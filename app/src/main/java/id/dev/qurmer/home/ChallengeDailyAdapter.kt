package id.dev.qurmer.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.data.model.ChallengeDataResponse
import id.dev.qurmer.data.model.ChallengeDataResponse.Challenge

class ChallengeDailyAdapter (val context : Context, val data : List<Challenge>)
    : RecyclerView.Adapter<QuoteAdapter.ViewHolder>(){

    inner class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: QuoteAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}