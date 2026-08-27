package com.cobasendiri.kasirmudahkmp.core.di

import com.cobasendiri.kasirmudahkmp.core.data.preference.ProfilePreferenceManager
import org.koin.dsl.module

val preferenceModule = module {
    single<ProfilePreferenceManager> { ProfilePreferenceManager(get()) }
}