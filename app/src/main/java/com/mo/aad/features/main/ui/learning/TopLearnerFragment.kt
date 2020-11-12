package com.mo.aad.features.main.ui.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mo.aad.R
import com.mo.aad.extensions.hide
import com.mo.aad.extensions.show
import com.mo.aad.features.main.ui.LazyFragment
import com.mo.aad.features.main.viewmodel.MainViewModel
import com.mo.aad.network.Status
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
@FlowPreview
class TopLearnerFragment : LazyFragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler,container,false)
    }


    override fun lazyInit() {

        viewModel.getTopLearningUsers()

        viewModel.topLearnersLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    loading.show()
                }
                Status.SUCCESS -> {
                    loading.hide()
                    it.data?.let { items ->
                        recyclerView.show()
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.adapter = LearningAdapter(items = items)
                    }
                }
                Status.ERROR -> {
                    loading.hide()
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}