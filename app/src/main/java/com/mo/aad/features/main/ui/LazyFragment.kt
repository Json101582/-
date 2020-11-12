package com.mo.aad.features.main.ui

import androidx.fragment.app.Fragment

/**
 * @author Jsonshi
 * @date 2020/11/12
 * <p>
 * Description:
 */
abstract class LazyFragment:Fragment() {
    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()
}