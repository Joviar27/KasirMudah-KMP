package com.cobasendiri.kasirmudahkmp.di

import com.cobasendiri.kasirmudahkmp.core.di.databaseModule
import com.cobasendiri.kasirmudahkmp.core.di.repositoryModule
import com.cobasendiri.kasirmudahkmp.ui.features.tab.di.tabModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect val platformModule: Module

val allModules = listOf(
    databaseModule,
    platformModule,
    repositoryModule,
    useCaseModule,
    appModule,
    tabModule
)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(allModules)
}