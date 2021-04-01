package com.thousand.bus.main.presentation.driver.carlist

import com.thousand.bus.entity.Car
import com.thousand.bus.entity.CreateTravel
import com.thousand.bus.global.base.BaseMvpView

interface CarListDriverView: BaseMvpView {

    fun showCarListData(dataList: List<Car>)

    fun openHomeDriverFragment(carId:Int, carTypeId: Int)

    fun openUpcomingDriverFragment(upcomingId : Int)

    fun openPassengerFragment(carId: Int)



}