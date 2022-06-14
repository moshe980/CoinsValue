package com.giniapps.coinsvalue.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giniapps.coinsvalue.R
import com.giniapps.coinsvalue.view.boundary.CoinBoundary

class MyAdapter(private val mList: List<CoinBoundary>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener
    private val greater = 1
    private val less = 0

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewGrater = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_greater_than_dollar_item, parent, false)
        val viewLess = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_less_then_dollar_item, parent, false)
        if (getItemViewType(viewType) == greater) {
            return ViewHolder(viewGrater, mListener)
        } else {
            return ViewHolder(viewLess, mListener)

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsView = mList[position]

        holder.imageView.setImageResource(R.drawable.coin_comp_win_300_300)

        holder.textView.text = itemsView.name


    }

    override fun getItemViewType(position: Int): Int {
        if (mList[position].isGreaterTanDollar) {
            return greater
        } else
            return less

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}