package com.olivinskij.portfolioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                MainApp()
        }
    }
}

val sampleImages = listOf(
    R.drawable.photo1,
    R.drawable.photo2,
    R.drawable.photo3,
    R.drawable.photo4,
    R.drawable.photo1,
    R.drawable.photo2,
    R.drawable.photo3,
    R.drawable.photo4,
    R.drawable.photo1,
    R.drawable.photo2,
    R.drawable.photo3,
    R.drawable.photo4
)

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
        MainApp()
}
@Composable
fun MainApp() {
    val poppinsSemiBold = FontFamily(Font(R.font.poppinssemibold))
    var selectedTab by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bgsplash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.ava),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 150.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.bgava),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 220.dp)
        )

        Text(
            text = "Olivinskiy",
            color = Color.White,
            fontFamily = poppinsSemiBold,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 330.dp)
        )

        Text(
            text = "@olivinskiy",
            color = Color.Gray,
            fontFamily = poppinsSemiBold,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 360.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIcon(R.drawable.github, "123")
                SocialIcon(R.drawable.facebook, "456")
                SocialIcon(R.drawable.figma, "789")
                SocialIcon(R.drawable.instagram, "101")
            }

            Spacer(modifier = Modifier.height(24.dp))

            TabMenu(selectedTab = selectedTab, onTabSelected = { selectedTab = it })

            TabContent(selectedTab = selectedTab, sampleImages = sampleImages, poppinsSemiBold = poppinsSemiBold)
        }
    }
}
@Composable
fun TabContent(selectedTab: Int, sampleImages: List<Int>, poppinsSemiBold: FontFamily) {
    Crossfade(targetState = selectedTab, animationSpec = tween(durationMillis = 300)) { tab ->
        when (tab) {
            0 -> Text(
                stringResource(id = R.string.infome),
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = poppinsSemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            1 -> ImageGrid(images = sampleImages)
            2 -> IconsSocials()
        }
    }
}
@Composable
fun ImageGrid(images: List<Int>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
    }
}
@Composable
fun TabMenu(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf(
        stringResource(id = R.string.info),
        stringResource(id = R.string.projects),
        stringResource(id = R.string.socialmedia)
    )
    val density = LocalDensity.current

    val tabWidths = remember { mutableStateListOf(*Array(tabs.size) { 0.dp }) }
    val tabHeights = remember { mutableStateListOf(*Array(tabs.size) { 0.dp }) }

    val transition = updateTransition(targetState = selectedTab, label = "TabTransition")

    val indicatorOffset by transition.animateDp(
        transitionSpec = { tween(300) },
        label = "IndicatorOffset"
    ) { index ->
        tabWidths.take(index).fold(0.dp) { acc, w -> acc + w + 16.dp }
    }

    val indicatorWidth by transition.animateDp(
        transitionSpec = { tween(300) },
        label = "IndicatorWidth"
    ) { index -> tabWidths[index] }

    val indicatorY by transition.animateDp(
        transitionSpec = { tween(300) },
        label = "IndicatorY"
    ) { index -> tabHeights[index] + 4.dp }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            tabs.forEachIndexed { index, title ->
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .clickable { onTabSelected(index) }
                        .onGloballyPositioned { coords ->
                            val widthDp = with(density) { coords.size.width.toDp() }
                            val heightDp = with(density) { coords.size.height.toDp() }

                            tabWidths[index] = widthDp
                            tabHeights[index] = heightDp
                        },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun SocialIcon(iconRes: Int, count: String) {
    val poppinsregular = FontFamily(Font(R.font.akrobat_bold))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = count,
            fontFamily = poppinsregular,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}



@Composable
fun IconsSocials() {
    val uriHandler = LocalUriHandler.current

    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = Modifier.offset(y = 15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.telegram_frame),
                contentDescription = "Telegram",
                modifier = Modifier.clickable {
                    uriHandler.openUri("https://t.me/olivinskij")
                }
            )
            Image(
                painter = painterResource(id = R.drawable.web_frame),
                contentDescription = "Website",
                modifier = Modifier.clickable {
                    uriHandler.openUri("https://olivinskij.com")
                }
            )
        }

        Image(
            painter = painterResource(id = R.drawable.github_frame),
            contentDescription = "GitHub",
            modifier = Modifier
                .width(290.dp)
                .offset(y = 10.dp)
                .clickable {
                    uriHandler.openUri("https://github.com/Olivinskiy")
                }
        )
    }
}