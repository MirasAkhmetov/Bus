package com.thousand.bus.main.presentation.common.list

import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.base.BaseMvpView

interface ListDialogFragmentView : BaseMvpView {

    fun showData(isMultiple: Boolean, dataList: List<ListItem>)
    fun closeThisFragmentWithResult(listItem: ListItem)
    fun closeThisFragmentWithResults(listItems: List<ListItem>)

}