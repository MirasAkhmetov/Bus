package com.thousand.bus.main.presentation.welcome

import android.os.Bundle
import com.thousand.bus.R
import com.thousand.bus.global.base.BaseFragment

class WelcomeFragment : BaseFragment(){

    companion object{

        val TAG = "WelcomeFragment"

        fun newInstance(): WelcomeFragment =
            WelcomeFragment()

    }

    override val layoutRes: Int
        get() = R.layout.fragment_welcome

    override fun setUp(savedInstanceState: Bundle?) {

    }

}