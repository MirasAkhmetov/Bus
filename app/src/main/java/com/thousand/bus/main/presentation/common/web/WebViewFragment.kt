package com.thousand.bus.main.presentation.common.web

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thousand.bus.R
import com.thousand.bus.global.extension.visible
import kotlinx.android.synthetic.main.fragment_web_view.*
import kotlinx.android.synthetic.main.include_toolbar.*

class WebViewFragment : Fragment(){

    companion object{

        val TAG = "WebViewFragment"

        private val BUNDLE_CONTENT = "content"

        fun newInstance(content: String): WebViewFragment =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_CONTENT, content)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        Handler().postDelayed(
            {
                webView?.loadDataWithBaseURL(
                    null,
                    arguments?.getString(BUNDLE_CONTENT),
                    "text/html",
                    "UTF-8",
                    null
                )
            },
            500
        )
    }

}