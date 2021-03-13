package com.thousand.bus.main.presentation.common.list

import android.os.Bundle
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.base.BaseDialogFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.di.MainScope
import kotlinx.android.synthetic.main.dialog_fragment_list.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ListDialogFragment(val onItemSelectedListener: (ListItem) -> Unit, val onItemsSelectedListener: (List<ListItem>) -> Unit): BaseDialogFragment(),
    ListDialogFragmentView {

    companion object {

        val TAG = "ListDialogFragment"

        private val BUNDLE_LIST_ITEMS = "list_items"
        private val BUNDLE_MULTIPLE = "multiple"

        fun newInstance(
            isMultiple: Boolean = false,
            dataList: ArrayList<ListItem>,
            onItemSelectedListener: (ListItem) -> Unit,
            onItemsSelectedListener: (List<ListItem>) -> Unit
        ): ListDialogFragment =
            ListDialogFragment(
                onItemSelectedListener,
                onItemsSelectedListener
            ).apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(BUNDLE_LIST_ITEMS, dataList)
                    putBoolean(BUNDLE_MULTIPLE, isMultiple)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: ListDialogPresenter

    @ProvidePresenter
    fun providePresenter(): ListDialogPresenter {
        getKoin().getScopeOrNull(MainScope.LIST_DIALOG_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.LIST_DIALOG_SCOPE, named(MainScope.LIST_DIALOG_SCOPE))
        val isMultiple = arguments?.getBoolean(BUNDLE_MULTIPLE)
        val dataList: List<ListItem>? = arguments?.getParcelableArrayList<ListItem>(
            BUNDLE_LIST_ITEMS
        )?.toList()
        return scope.get { parametersOf(isMultiple, dataList) }
    }

    override val layoutRes: Int
        get() = R.layout.dialog_fragment_list

    private val listAdapter =
        ListAdapter{
            closeThisFragmentWithResult(it)
        }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerDFL.adapter = listAdapter
        presenter.onFirstInit()
        btnReadyDFL.setOnClickListener { presenter.onReadyBtnClicked() }
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.LIST_DIALOG_SCOPE)
        super.onDestroy()
    }

    override fun showData(isMultiple: Boolean, dataList: List<ListItem>) {
        listAdapter.submitData(isMultiple, dataList)
        btnReadyDFL?.visible(isMultiple)
    }

    override fun closeThisFragmentWithResult(listItem: ListItem) {
        onItemSelectedListener.invoke(listItem)
        dismiss()
    }

    override fun closeThisFragmentWithResults(listItems: List<ListItem>) {
        onItemsSelectedListener.invoke(listItems)
        dismiss()
    }
}