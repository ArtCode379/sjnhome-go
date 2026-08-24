package sjnenterprises.household.sjnhomego.ui.composable.screen.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import sjnenterprises.household.sjnhomego.R
import sjnenterprises.household.sjnhomego.data.model.Product
import sjnenterprises.household.sjnhomego.ui.composable.shared.MBWRKContentWrapper
import sjnenterprises.household.sjnhomego.ui.composable.shared.MBWRKEmptyView
import sjnenterprises.household.sjnhomego.ui.state.DataUiState
import sjnenterprises.household.sjnhomego.ui.viewmodel.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel(),
) {
    val productState by viewModel.productDetailsState.collectAsState()
    var cartAdded by remember { mutableStateOf(false) }

    LaunchedEffect(productId) {
        viewModel.observeProductDetails(productId)
    }
    LaunchedEffect(cartAdded) {
        if (cartAdded) {
            delay(2000)
            cartAdded = false
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        MBWRKContentWrapper(
            dataState = productState,
            dataPopulated = {
                ProductDetails(
                    product = (productState as DataUiState.Populated).data,
                    onAddToCart = {
                        viewModel.addProductToCart()
                        cartAdded = true
                    },
                )
            },
            dataEmpty = {
                MBWRKEmptyView(
                    primaryText = stringResource(R.string.mbwrk_product_details_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
        AnimatedVisibility(
            visible = cartAdded,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically { it },
            exit = fadeOut(),
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
            ) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                    Text("Added to cart", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
private fun ProductDetails(product: Product, onAddToCart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.padding(20.dp)) {
            Text(product.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text(
                text = "£%.2f".format(product.price),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(12.dp))
            Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.primaryContainer) {
                Text(stringResource(product.category.titleRes), modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
            }
            Spacer(Modifier.height(20.dp))
            Text(product.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(28.dp))
            Button(onClick = onAddToCart, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.mbwrk_button_add_to_cart_label))
            }
        }
    }
}
