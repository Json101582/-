package com.mo.aad.extensions

import android.view.View

/**
 * @author Jsonshi
 * @date 2020/11/13
 * <p>
 * Description:所有RecyclerView Item点击事件
 */
interface OnItemViewClickListener {
    fun onItemClick(itemView: View, position: Int)
}