package sjnenterprises.household.sjnhomego.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("About", style = MaterialTheme.typography.headlineMedium)
        Card(shape = RoundedCornerShape(16.dp)) {
            SettingLine(icon = { Icon(Icons.Default.Business, null) }, title = "Company", value = "SJN ENTERPRISES LTD")
            SettingLine(icon = { Icon(Icons.Default.Info, null) }, title = "App version", value = "1.0")
        }
        Text("Support", style = MaterialTheme.typography.titleLarge)
        Text("Questions about a product or an existing reservation? Our website is the quickest way to reach us.")
        Button(
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://sjn-enterprises.surf/")))
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(Icons.Default.SupportAgent, contentDescription = null)
            Text(" Customer Support")
        }
    }
}

@Composable
private fun SettingLine(
    icon: @Composable () -> Unit,
    title: String,
    value: String,
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        icon()
        Column {
            Text(title, style = MaterialTheme.typography.labelLarge)
            Text(value, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

