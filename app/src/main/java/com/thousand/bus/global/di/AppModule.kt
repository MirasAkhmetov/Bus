package com.thousand.bus.global.di

import com.thousand.bus.main.di.interactorAndRepositoryModule
import com.thousand.bus.main.di.mainModule

val appModule = listOf(networkModule, utilModule, interactorAndRepositoryModule, mainModule)