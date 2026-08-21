package com.cobasendiri.kasirmudahkmp.di

import com.arkivanov.decompose.ComponentContext
import com.cobasendiri.kasirmudahkmp.ui.features.RootComponent
import org.koin.dsl.module

val appModule = module {
    factory<RootComponent> { (ctx: ComponentContext) ->
        RootComponent(ctx)
    }
}