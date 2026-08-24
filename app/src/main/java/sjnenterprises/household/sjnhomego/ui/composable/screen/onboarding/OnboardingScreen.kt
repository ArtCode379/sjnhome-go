package sjnenterprises.household.sjnhomego.ui.composable.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import sjnenterprises.household.sjnhomego.ui.viewmodel.MBWRKOnboardingVM

private data class OnboardingPage(
    val title: String,
    val description: String,
    val imageUrl: String,
)

private val onboardingPages = listOf(
    OnboardingPage(
        title = "Useful finds for every room",
        description = "Browse practical, considered homeware that makes daily routines feel easier.",
        imageUrl = "https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?w=1200",
    ),
    OnboardingPage(
        title = "Everything for work and life",
        description = "Discover stationery and accessories selected for busy desks, bags, and days.",
        imageUrl = "https://images.unsplash.com/photo-1497215728101-856f4ea42174?w=1200",
    ),
    OnboardingPage(
        title = "Reserve now, collect with ease",
        description = "Build your basket, confirm your reservation, and collect in store within 24 hours.",
        imageUrl = "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=1200",
    ),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: MBWRKOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val onboardingSetState by viewModel.onboardingSetState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })

    LaunchedEffect(onboardingSetState) {
        if (onboardingSetState) {
            onNavigateToHomeScreen()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { pageIndex ->
            val page = onboardingPages[pageIndex]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                AsyncImage(
                    model = page.imageUrl,
                    contentDescription = page.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(Modifier.height(28.dp))
                Text(text = page.title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            onboardingPages.indices.forEach { index ->
                Surface(
                    modifier = Modifier.size(if (index == pagerState.currentPage) 10.dp else 8.dp),
                    shape = CircleShape,
                    color = if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                ) {}
            }
        }
        Spacer(Modifier.height(24.dp))
        if (pagerState.currentPage == onboardingPages.lastIndex) {
            Button(
                onClick = viewModel::setOnboarded,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Get Started")
            }
        } else {
            Box(modifier = Modifier.height(48.dp))
        }
    }
}
