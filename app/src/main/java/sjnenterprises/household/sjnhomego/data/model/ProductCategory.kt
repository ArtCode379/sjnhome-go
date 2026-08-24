package sjnenterprises.household.sjnhomego.data.model

import androidx.annotation.StringRes
import sjnenterprises.household.sjnhomego.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    HOME(R.string.mbwrk_category_home),
    STATIONERY(R.string.mbwrk_category_stationery),
    ACCESSORIES(R.string.mbwrk_category_accessories),
    SEASONAL(R.string.mbwrk_category_seasonal),
}

