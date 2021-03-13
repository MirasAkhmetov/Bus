package com.thousand.bus.global.di


import com.thousand.bus.global.functional.NetworkHandler
import com.thousand.bus.global.system.AppSchedulers
import com.thousand.bus.global.system.ResourceManager
import com.thousand.bus.global.system.SchedulersProvider
import com.thousand.bus.global.utils.ErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single { AppSchedulers() as SchedulersProvider }
    single { ResourceManager(androidContext()) }
    single { ErrorHandler(get()) }
    single { NetworkHandler(androidContext()) }
}