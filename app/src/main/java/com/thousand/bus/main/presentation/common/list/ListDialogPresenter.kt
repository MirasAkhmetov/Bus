package com.thousand.bus.main.presentation.common.list

import com.arellomobile.mvp.InjectViewState
import com.thousand.bus.entity.ListItem
import com.thousand.bus.global.presentation.BasePresenter

@InjectViewState
class ListDialogPresenter(
    private val isMultiple: Boolean,
    private val dataList: List<ListItem>
): BasePresenter<ListDialogFragmentView>(){

    fun onFirstInit(){
        viewState?.showData(isMultiple, dataList)
    }

    fun onReadyBtnClicked(){
        if (!isMultiple){
            dataList.forEach {
                if (it.selected){
                    viewState?.closeThisFragmentWithResult(it)
                    return@forEach
                }
            }
        }else{
            val tempList: MutableList<ListItem> = mutableListOf()
            dataList.forEach {
                if (it.selected)
                    tempList.add(it)
            }
            viewState?.closeThisFragmentWithResults(tempList)
        }
    }

}