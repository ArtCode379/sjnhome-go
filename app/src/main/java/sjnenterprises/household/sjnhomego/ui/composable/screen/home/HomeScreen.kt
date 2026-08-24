package sjnenterprises.household.sjnhomego.ui.composable.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import sjnenterprises.household.sjnhomego.R
import sjnenterprises.household.sjnhomego.data.model.Product
import sjnenterprises.household.sjnhomego.data.model.ProductCategory
import sjnenterprises.household.sjnhomego.ui.composable.shared.MBWRKContentWrapper
import sjnenterprises.household.sjnhomego.ui.composable.shared.MBWRKEmptyView
import sjnenterprises.household.sjnhomego.ui.state.DataUiState
import sjnenterprises.household.sjnhomego.ui.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()
    var category by remember { mutableStateOf<ProductCategory?>(null) }

    MBWRKContentWrapper(
        dataState = productsState,
        dataPopulated = {
            val products = (productsState as DataUiState.Populated).data
            ProductCatalogue(
                products = products,
                selectedCategory = category,
                modifier = modifier,
                onCategorySelected = { category = it },
                onProductClick = onNavigateToProductDetails,
            )
        },
        dataEmpty = {
            MBWRKEmptyView(
                primaryText = stringResource(R.string.mbwrk_products_state_empty_primary_text),
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun ProductCatalogue(
    products: List<Product>,
    selectedCategory: ProductCategory?,
    onCategorySelected: (ProductCategory?) -> Unit,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val featured = products.take(4)
    val pagerState = rememberPagerState(pageCount = { featured.size })

    LaunchedEffect(pagerState.pageCount) {
        while (pagerState.pageCount > 1) {
            delay(4000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .padding(top = 12.dp),
            pageSpacing = 12.dp,
        ) { page ->
            val product = featured[page]
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .clickable { onProductClick(product.id) },
                shape = RoundedCornerShape(18.dp),
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Crop,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(product.title, style = MaterialTheme.typography.titleMedium)
                    Text("£%.2f".format(product.price), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        }
        LazyRow(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                FilterChip(selected = selectedCategory == null, onClick = { onCategorySelected(null) }, label = { Text("All") })
            }
            items(ProductCategory.entries.size) { index ->
                val item = ProductCategory.entries[index]
                FilterChip(
                    selected = selectedCategory == item,
                    onClick = { onCategorySelected(item) },
                    label = { Text(stringResource(item.titleRes)) },
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(products.filter { selectedCategory == null || it.category == selectedCategory }) { product ->
                ProductCard(product = product, onClick = { onProductClick(product.id) })
            }
        }
    }
}

@Composable
private fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.1f)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Text(product.title, style = MaterialTheme.typography.titleMedium, maxLines = 2)
            Text(stringResource(product.category.titleRes), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("£%.2f".format(product.price), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}

