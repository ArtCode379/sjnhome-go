package sjnenterprises.household.sjnhomego.di

import sjnenterprises.household.sjnhomego.ui.viewmodel.AppViewModel
import sjnenterprises.household.sjnhomego.ui.viewmodel.CartViewModel
import sjnenterprises.household.sjnhomego.ui.viewmodel.CheckoutViewModel
import sjnenterprises.household.sjnhomego.ui.viewmodel.MBWRKOnboardingVM
import sjnenterprises.household.sjnhomego.ui.viewmodel.OrderViewModel
import sjnenterprises.household.sjnhomego.ui.viewmodel.ProductDetailsViewModel
import sjnenterprises.household.sjnhomego.ui.viewmodel.ProductViewModel
import sjnenterprises.household.sjnhomego.ui.viewmodel.MBWRKSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        MBWRKSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        MBWRKOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}