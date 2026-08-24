package sjnenterprises.household.sjnhomego.di

import sjnenterprises.household.sjnhomego.data.repository.CartRepository
import sjnenterprises.household.sjnhomego.data.repository.MBWRKOnboardingRepo
import sjnenterprises.household.sjnhomego.data.repository.OrderRepository
import sjnenterprises.household.sjnhomego.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        MBWRKOnboardingRepo(
            mbwrkOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}