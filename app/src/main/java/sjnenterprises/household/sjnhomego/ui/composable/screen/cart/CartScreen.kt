package sjnenterprises.household.sjnhomego.ui.composable.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import sjnenterprises.household.sjnhomego.ui.composable.shared.MBWRKContentWrapper
import sjnenterprises.household.sjnhomego.ui.composable.shared.MBWRKEmptyView
import sjnenterprises.household.sjnhomego.ui.state.CartItemUiState
import sjnenterprises.household.sjnhomego.ui.state.DataUiState
import sjnenterprises.household.sjnhomego.ui.viewmodel.CartViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val cartItemsState by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()

    MBWRKContentWrapper(
        dataState = cartItemsState,
        dataPopulated = {
            CartContent(
                items = (cartItemsState as DataUiState.Populated).data,
                total = totalPrice,
                modifier = modifier,
                onPlus = viewModel::incrementProductInCart,
                onMinus = { item ->
                    if (item.quantity == 1) {
                        viewModel.deleteFromCart(item.productId)
                    } else {
                        viewModel.decrementItemInCart(item.productId)
                    }
                },
                onCheckout = onNavigateToCheckoutScreen,
            )
        },
        dataEmpty = {
            MBWRKEmptyView(primaryText = "Your cart is ready for something useful", modifier = modifier.fillMaxSize())
        },
    )
}

@Composable
private fun CartContent(
    items: List<CartItemUiState>,
    total: Double,
    onPlus: (Int) -> Unit,
    onMinus: (CartItemUiState) -> Unit,
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items, key = { it.productId }) { item ->
                Card(shape = RoundedCornerShape(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            model = item.productImageUrl,
                            contentDescription = item.productTitle,
                            modifier = Modifier.size(64.dp),
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp),
                        ) {
                            Text(item.productTitle, style = MaterialTheme.typography.titleMedium)
                            Text("£%.2f".format(item.productPrice), color = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onMinus(item) }) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease quantity")
                        }
                        Text(item.quantity.toString(), fontWeight = FontWeight.Bold)
                        IconButton(onClick = { onPlus(item.productId) }) {
                            Icon(Icons.Default.Add, contentDescription = "Increase quantity")
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", style = MaterialTheme.typography.titleLarge)
                Text("£%.2f".format(total), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            }
            Button(onClick = onCheckout, modifier = Modifier.fillMaxWidth()) {
                Text("Proceed to Checkout")
            }
        }
    }
}

