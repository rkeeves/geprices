package com.github.rkeeves.geprices.ui.details

import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.rkeeves.geprices.R
import com.github.rkeeves.geprices.data.local.Commodity
import java.sql.Timestamp
import java.util.*

@BindingAdapter("today_percent")
fun TextView.setTrendPercent(commodity: Commodity?) {
    commodity?.let {
        val dbl = if (commodity.currentPrice == 0) 0.0 else (commodity.todayPriceChange.toDouble() / commodity.currentPrice.toDouble()) * 100.0
        text = resources.getString(R.string.trend_percent_text, dbl.toInt().toString())
    }
}

@BindingAdapter("today_trend")
fun ImageView.setTrendImageTodayPriceChange(priceChange: Int?) {
    priceChange?.let {
        setImageResource(when  {
            it > 0 -> R.drawable.positive
            it < 0 -> R.drawable.negative
            else -> R.drawable.no_change
        })
    }
}

@BindingAdapter("today_price")
fun TextView.setTodayPrice(commodity: Commodity?) {
    commodity?.let {
        text = (commodity.currentPrice + commodity.todayPriceChange).toString()
    }
}

@BindingAdapter("price")
fun TextView.setPrice(price: Int?) {
    price?.let {
        text = it.toString()
    }
}

@BindingAdapter("trend_percent")
fun TextView.setTrendPercent(changePercent: Double?) {
    changePercent?.let {
        text = resources.getString(R.string.trend_percent_text, it.toString())
    }
}

@BindingAdapter("trend_image")
fun ImageView.setTrendImage(changePercent: Double?) {
    changePercent?.let {
        setImageResource(when  {
            it > 0.0 -> R.drawable.positive
            it < 0.0 -> R.drawable.negative
            else -> R.drawable.no_change
        })
    }
}

@BindingAdapter("timestamp")
fun TextView.setTimestamp(item: Timestamp?) {
    item?.let {
        text = timestampAsDateString(item)
    }
}

fun timestampAsDateString(timestamp: Timestamp) :String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp.time
    return DateFormat.format("yyyy. MM. dd. HH:mm",calendar).toString()
}

fun priceOf(base: Int, diffPercent: Double): Int {
    return base.toDouble()
            .times(diffPercent.plus(100.0))
            .div(100.0)
            .toInt()

}

@BindingAdapter("short_term_price")
fun TextView.setShortTermPrice(commodity: Commodity?) {
    commodity?.let {
        text = priceOf(commodity.currentPrice, commodity.shortTermChange).toString()
    }
}

@BindingAdapter("mid_term_price")
fun TextView.setMidTermPrice(commodity: Commodity?) {
    commodity?.let {
        text = priceOf(commodity.currentPrice, commodity.midTermChange).toString()
    }
}

@BindingAdapter("long_term_price")
fun TextView.setLongTermPrice(commodity: Commodity?) {
    commodity?.let {
        text = priceOf(commodity.currentPrice, commodity.longTermChange).toString()
    }
}