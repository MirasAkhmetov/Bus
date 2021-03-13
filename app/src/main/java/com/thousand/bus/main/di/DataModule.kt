package com.thousand.bus.main.di

import com.thousand.bus.main.data.interactor.AuthInteractor
import com.thousand.bus.main.data.interactor.CustomerInteractor
import com.thousand.bus.main.data.interactor.DriverInteractor
import com.thousand.bus.main.data.interactor.ListInteractor
import com.thousand.bus.main.data.repository.*
import org.koin.dsl.module

val interactorAndRepositoryModule = module {

    single<AuthRepository>{ AuthRepositoryImpl(get()) }
    single { AuthInteractor(get(), get()) }

    single<ListRepository>{ ListRepositoryImpl(get()) }
    single { ListInteractor(get(), get()) }

    single<CustomerRepository>{ CustomerRepositoryImpl(get()) }
    single { CustomerInteractor(get(), get()) }

    single<DriverRepository>{ DriverRepositoryImpl(get()) }
    single { DriverInteractor(get(), get()) }

}