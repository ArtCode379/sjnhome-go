package sjnenterprises.household.sjnhomego.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import sjnenterprises.household.sjnhomego.ui.state.DataUiState
import sjnenterprises.household.sjnhomego.ui.viewmodel.CheckoutViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalidState by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    val isButtonEnabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotBlank() &&
                viewModel.customerLastName.isNotBlank() &&
                viewModel.customerEmail.isNotBlank()
        }
    }

    if (orderState is DataUiState.Populated) {
        CheckoutDialog(
            order = (orderState as DataUiState.Populated).data,
            onConfirm = onNavigateToOrdersScreen,
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Reserve your order", style = MaterialTheme.typography.headlineMedium)
        Text("Enter your details. Your items will be held in store for 24 hours.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        CheckoutTextField(
            input = viewModel.customerFirstName,
            onInputChange = viewModel::updateCustomerFirstName,
            labelText = "First name",
            modifier = Modifier.fillMaxWidth(),
        )
        CheckoutTextField(
            input = viewModel.customerLastName,
            onInputChange = viewModel::updateCustomerLastName,
            labelText = "Address",
            modifier = Modifier.fillMaxWidth(),
        )
        CheckoutTextField(
            input = viewModel.customerEmail,
            onInputChange = viewModel::updateCustomerEmail,
            labelText = "Email for reservation confirmation",
            modifier = Modifier.fillMaxWidth(),
            isError = emailInvalidState,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )
        if (emailInvalidState) {
            Text("Enter a valid email address", color = MaterialTheme.colorScheme.error)
        }
        Button(
            onClick = viewModel::placeOrder,
            enabled = isButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Place Reservation")
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        ),
    )
}

