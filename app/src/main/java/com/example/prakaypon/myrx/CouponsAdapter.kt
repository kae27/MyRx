package com.example.prakaypon.myrx

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.coupon_item.view.*
import org.jetbrains.annotations.NotNull

class CouponsAdapter(val list: List<Coupon>,private val listener: OnItemClickListener) : RecyclerView.Adapter<CouponsAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.coupon_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bindView(list[position], listener,position)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {

        private lateinit var textStore:TextView
        private lateinit var textCoupon:TextView

        fun bindView( coupon:Coupon,listener:CouponsAdapter.OnItemClickListener, position: Int)
        {
            textStore = itemView.findViewById(R.id.store)
            textCoupon = itemView.findViewById<TextView>(R.id.coupon).apply { this.text=coupon.coupon } //setText with apply

            textStore.text = coupon.store //mormal setText


            itemView.setOnClickListener {
                listener.onItemClick(position)
            }

        }

    }

    interface OnItemClickListener
    {
        fun onItemClick(position: Int)
    }
}