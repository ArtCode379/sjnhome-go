package sjnenterprises.household.sjnhomego.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import sjnenterprises.household.sjnhomego.data.entity.OrderEntity

@Composable
fun CheckoutDialog(
    order: OrderEntity,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onConfirm,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("View Orders")
            }
        },
        title = {
            Text("Reservation #${order.orderNumber}", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Your reservation is confirmed.")
                Text("We will hold these items for you in store for 24 hours.")
                Text(order.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Total: £%.2f".format(order.price), color = MaterialTheme.colorScheme.primary)
            }
        },
    )
}

