package com.mo.aad.features.poked.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.mo.aad.R
import com.mo.aad.extensions.OnItemViewClickListener
import com.mo.aad.features.poked.viewmodel.PokedViewModel
import com.mo.aad.network.Status
import kotlinx.android.synthetic.main.activity_poked.*
import kotlinx.android.synthetic.main.layout_item.view.*
import kotlinx.android.synthetic.main.title_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel




@ExperimentalCoroutinesApi
@FlowPreview
class PokedActivity : AppCompatActivity(), OnItemViewClickListener {

    private val mPokedModel:PokedViewModel by viewModel()
    private lateinit var mPokedAdapter: PokedAdapter
    private val gridLayoutManager:GridLayoutManager by lazy {
        GridLayoutManager(this@PokedActivity,2)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poked)
        //设置状态栏
        immersionBar {
            fullScreen(true)
            fitsSystemWindows(true)
            navigationBarColor(R.color.colorPrimary)
            statusBarDarkFont(true, 0.2f)
        }
        titleView?.apply {
            title_tv.text = "宠物秀"
            title_tv.setTextColor(Color.BLUE)
        }
//        mPokedModel.getPokedChildList(20, 0)
//        swipeRefreshLayout.setOnRefreshListener {
//            mPokedModel.getPokedChildList(20, 0)
//        }
//        mPokedModel.mPokemonListLiveData.observe(this, {
//            when (it.status) {
//                Status.LOADING -> {
//                    swipeRefreshLayout.isRefreshing = true
//                }
//                Status.SUCCESS -> {
//                    swipeRefreshLayout.isRefreshing = false
//                    it.data?.let { items ->
//                        recyclerView.layoutManager = gridLayoutManager
//                        mPokedAdapter = PokedAdapter(items = items, this)
//                        recyclerView.adapter = mPokedAdapter
//                    }
//                }
//                Status.ERROR -> {
//                    swipeRefreshLayout.isRefreshing = false
//                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
//        daoUpdateUi()
          daoUpdateFlowUi()
    }


    override fun onItemClick(itemView: View, position: Int) {
        val mainIntent = Intent(this@PokedActivity, PokedDetailActivity::class.java)
        mainIntent.putExtra("name", mPokedAdapter.items[position].name)
        mainIntent.putExtra("url", mPokedAdapter.items[position].getImageUrl())
        mainIntent.putExtra("pos", position.toString())
        val bundle = itemView.transformationLayout.withView(itemView, "myTransitionName$position")
        mainIntent.putExtra("TransformationParams", itemView.transformationLayout.getParcelableParams())
        startActivity(mainIntent, bundle)
    }


    //测试
    private fun daoUpdateUi(){
        mPokedModel.getListFlowData(20,0)
        swipeRefreshLayout.setOnRefreshListener {
            mPokedModel.getListFlowData(20,0)
            swipeRefreshLayout.isRefreshing = false
        }
        mPokedModel.mPokemonLiveData1.observe(this,{
            when (it.status) {
                Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    it.data?.let { items->
                        recyclerView.layoutManager = gridLayoutManager
                        mPokedAdapter = PokedAdapter(items = items.results, this)
                        recyclerView.adapter = mPokedAdapter
                    }
                }
                Status.ERROR -> {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    //测试数据库操作数据
    private fun daoUpdateFlowUi(){
        mPokedModel.getPokedChildList(20,0)
        swipeRefreshLayout.setOnRefreshListener {
            mPokedModel.getPokedChildList(20,0)
        }
        mPokedModel.mPokemonListLiveData.observe(this,{
            when (it.status) {
                Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    it.data?.let { items->
                        recyclerView.layoutManager =  gridLayoutManager
                        mPokedAdapter = PokedAdapter(items = items, this)
                        recyclerView.adapter = mPokedAdapter
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

