package nick.mirosh.newsappkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import nick.mirosh.newsapp.di.androidModule
import nick.mirosh.newsappkmp.di.KoinContainer
import nick.mirosh.newsappkmp.ui.HomeNavigation
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KoinContainer.initKoin(
            platformSpecificModule = androidModule
        ) {
            androidContext(applicationContext)
        }

        setContent {
            HomeNavigation()
        }
    }

}

