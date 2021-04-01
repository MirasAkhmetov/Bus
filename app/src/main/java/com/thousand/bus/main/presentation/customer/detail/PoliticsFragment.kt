package com.thousand.bus.main.presentation.customer.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thousand.bus.R

class PoliticsFragment : Fragment() {

    companion object{

        val TAG = "PoliticsFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_politics, container, false)
    }
}