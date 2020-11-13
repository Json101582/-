package com.mo.aad.features.poked.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mo.aad.R
import com.mo.aad.extensions.OnItemViewClickListener
import com.mo.aad.features.poked.viewmodel.PokedViewModel
import com.mo.aad.network.Status
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel


class PokedActivity : AppCompatActivity(),OnItemViewClickListener {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val mPokedModel:PokedViewModel by viewModel()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_recycler)
        mPokedModel.getPokedList(20, 0)
        swipeRefreshLayout.setOnRefreshListener {
            mPokedModel.getPokedList(20, 0)
        }
        mPokedModel.mPokemonLiveData.observe(this,{
            when (it.status) {
                Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    it.data?.let { items ->
                        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                        recyclerView.adapter = PokedAdapter(items = items.results,this)
                    }
                }
                Status.ERROR -> {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onItemClick(itemView: View, position: Int) {
        Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
    }
}