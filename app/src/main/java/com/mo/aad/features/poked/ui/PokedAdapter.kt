package com.mo.aad.features.poked.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mo.aad.R
import com.mo.aad.extensions.OnItemViewClickListener
import com.mo.aad.features.poked.data.Pokemon
import kotlinx.android.synthetic.main.layout_item.view.*

/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:
 */
class PokedAdapter(val items: List<Pokemon>,var mOnItemViewClickListener:OnItemViewClickListener) : RecyclerView.Adapter<PoKedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoKedViewHolder {
       return PoKedViewHolder(
           itemView = LayoutInflater.from(parent.context).inflate(
               R.layout.layout_item,
               parent,
               false
           )
       )
    }

    override fun onBindViewHolder(holder: PoKedViewHolder, position: Int) {
          holder.bind(items[position],mOnItemViewClickListener,position)
    }

    override fun getItemCount(): Int {
       return items.size
    }

}

class PoKedViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(item: Pokemon, mOnItemClickListener: OnItemViewClickListener, pos:Int) = with(itemView) {

        Glide.with(this)
            .load(item.getImageUrl())
            .placeholder(R.drawable.ic_launcher_background)
            .into(itemView.ivLearning)

        itemView.tvTitle.text = item.name
        itemView.tvDesc.text = "${item.name} Learning hours,  ${item.page}"
        itemView.setOnClickListener {
            mOnItemClickListener.onItemClick(it,pos)
        }
    }
}