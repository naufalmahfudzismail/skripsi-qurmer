package id.dev.qurmer.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import id.dev.qurmer.R
import id.dev.qurmer.data.model.QuoteDataResponse.Quote
import kotlinx.android.synthetic.main.card_quote.view.*

class QuoteAdapter(val context: Context, val data: List<Quote>) :
    RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun binding(quote: Quote) = try {

            val circular = CircularProgressDrawable(context)
            circular.strokeWidth = 5f
            circular.centerRadius = 20f
            circular.start()

            Glide.with(context).load(quote.imageUrl).asBitmap().placeholder(circular).into(view.img_content)

        } catch (e: Throwable) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_quote, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding(data[position])
    }
}