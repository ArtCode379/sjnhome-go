package sjnenterprises.household.sjnhomego.ui.composable.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import sjnenterprises.household.sjnhomego.data.entity.OrderEntity
import sjnenterprises.household.sjnhomego.ui.composable.shared.MBWRKContentWrapper
import sjnenterprises.household.sjnhomego.ui.composable.shared.MBWRKEmptyView
import sjnenterprises.household.sjnhomego.ui.state.DataUiState
import sjnenterprises.household.sjnhomego.ui.theme.BrandSuccess
import sjnenterprises.household.sjnhomego.ui.viewmodel.OrderViewModel
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val ordersState by viewModel.ordersState.collectAsState()

    MBWRKContentWrapper(
        dataState = ordersState,
        dataPopulated = {
            val orders = (ordersState as DataUiState.Populated).data.sortedByDescending { it.timestamp }
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(orders, key = { it.orderNumber }) { order ->
                    OrderCard(order)
                }
            }
        },
        dataEmpty = {
            MBWRKEmptyView(primaryText = "No orders yet", modifier = modifier.fillMaxSize())
        },
    )
}

@Composable
private fun OrderCard(order: OrderEntity) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Order #${order.orderNumber}", style = MaterialTheme.typography.titleMedium)
                Surface(shape = RoundedCornerShape(50), color = BrandSuccess.copy(alpha = 0.12f)) {
                    Text("Reserved", color = BrandSuccess, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                }
            }
            Text(order.timestamp.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")), color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(order.description)
            Text("£%.2f".format(order.price), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Collection window: 24 hours", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

