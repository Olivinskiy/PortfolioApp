package com.olivinskij.portfolioapp
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           SplashScreen()
        }
    }
}

@Composable
fun SplashScreen(navigateToMain: Boolean = true) {
    val context = LocalContext.current
    val akrobatFont = FontFamily(Font(R.font.akrobat_bold))
    var loadingText by remember { mutableStateOf("Привет!") }

    if (navigateToMain) {
        LaunchedEffect(Unit) {
            delay(2000)
            loadingText = "Загружаем информацию..."
            delay(2000)
            loadingText = "Входим в приложение..."
            delay(1000)
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as? ComponentActivity)?.finish()
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bgsplash), // твой фон
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // растягиваем по экрану
        )

        CircularProgressIndicator()

        Text(
            text = loadingText,
            color = Color.White,
            fontFamily = akrobatFont,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-50).dp)
        )
    }
}
