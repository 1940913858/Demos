package com.example.meetyou.myapplication.view.rvinscrollview

import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.meetyou.myapplication.R

class RcvAdapter : RecyclerView.Adapter<RcvAdapter.VHolder>() {

    var list:MutableList<Int> = ArrayList()

    init {
        for (i in 0..99){
            list.add(i)
        }
    }

    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val textTv:TextView=itemView.findViewById(R.id.pos_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
       val item:View = LayoutInflater.from(parent.context).inflate(R.layout.adapter,parent,false)
        return VHolder(item)
    }

    override fun getItemCount(): Int {
       return  list.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.textTv.text = position.toString()
        Log.e("senfa", "pos:$position")
    }
}