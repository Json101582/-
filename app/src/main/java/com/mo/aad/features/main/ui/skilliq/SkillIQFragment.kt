package com.mo.aad.features.main.ui.skilliq

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mo.aad.R
import com.mo.aad.extensions.OnItemViewClickListener
import com.mo.aad.features.main.ui.LazyFragment
import com.mo.aad.features.main.viewmodel.MainViewModel
import com.mo.aad.features.poked.ui.PokedDetailNewActivity
import com.mo.aad.network.Status
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SkillIQFragment : LazyFragment(),OnItemViewClickListener {

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler,container,false)
    }

    override fun lazyInit() {
        viewModel.getTopSkillIQUsers()
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getTopSkillIQUsers()
        }
        viewModel.topSkillsLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    it.data?.let { items ->
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.adapter = SkillIQAdapter(items = items,this)
                    }
                }
                Status.ERROR -> {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onItemClick(itemView: View, position: Int) {
        val mainIntent = Intent(activity, PokedDetailNewActivity::class.java)
        startActivity(mainIntent)
    }
}