package com.mo.aad.features.main.ui.learning

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mo.aad.R
import com.mo.aad.extensions.OnItemViewClickListener
import com.mo.aad.features.main.data.LearningHoursUser
import com.mo.aad.features.poked.ui.PokedDetailActivity
import kotlinx.android.synthetic.main.layout_item.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class LearningAdapter(val items: List<LearningHoursUser>,var mOnItemClickListener: OnItemViewClickListener) : RecyclerView.Adapter<LearningViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningViewHolder {
        return LearningViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LearningViewHolder, position: Int) {
        holder.bind(items[position],mOnItemClickListener,position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class LearningViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(item: LearningHoursUser, mOnItemClickListener: OnItemViewClickListener, pos:Int) = with(itemView) {

        Glide.with(this)
            .load(item.badgeUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(itemView.ivLearning)

        itemView.tvTitle.text = item.name
        itemView.tvDesc.text = "${item.hours} Learning hours,  ${item.country}"
        itemView.setOnClickListener {
            mOnItemClickListener.onItemClick(it,pos)
        }
    }
}


