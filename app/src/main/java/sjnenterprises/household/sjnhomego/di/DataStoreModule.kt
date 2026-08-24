package sjnenterprises.household.sjnhomego.di

import sjnenterprises.household.sjnhomego.data.datastore.MBWRKOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { MBWRKOnboardingPrefs(androidContext()) }
}