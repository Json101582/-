package com.mo.aad.features.poked.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar
import com.mo.aad.R
import com.mo.aad.features.poked.viewmodel.PokedViewModel
import com.mo.aad.network.Status
import kotlinx.android.synthetic.main.activity_poked.swipeRefreshLayout
import kotlinx.android.synthetic.main.activity_poked.titleView
import kotlinx.android.synthetic.main.activity_poked_detail.*
import kotlinx.android.synthetic.main.title_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class PokedDetailActivity : AppCompatActivity(R.layout.activity_poked_detail) {


    private val mPokedModel: PokedViewModel by viewModel()
    private var name: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置状态栏
        immersionBar {
            fullScreen(true)
            fitsSystemWindows(true)
            navigationBarColor(R.color.colorPrimary)
            statusBarDarkFont(true, 0.2f)
        }
        titleView?.apply {
            title_tv.text = "宠物信息"
            title_tv.setTextColor(Color.RED)
        }
        intent?.let {
            name = intent.getStringExtra("name")
            val url = intent.getStringExtra("url")
            val pos = intent.getStringExtra("pos")
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(pokedDetailImage)
            pokedDetailName.text = name
            pokedDetailAge.text = String.format("下标为%s的%s好可爱",pos,name)
        }
//        sendHttpDetail(name)
    }


    private fun sendHttpDetail(name: String?) {
        name?.run {
            mPokedModel.getPokedItem(name)
            swipeRefreshLayout.setOnRefreshListener {
                mPokedModel.getPokedItem(name)
            }
        }
        mPokedModel.mPokemonInfoLiveData.observe(this, { it ->
            when (it.status) {
                Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    it.data?.let {
                        Log.e("详情>>>", "onCreate: $it")
                    }
                }
                Status.ERROR -> {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}