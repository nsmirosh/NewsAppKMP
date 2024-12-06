package nick.mirosh.newsappkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.icerock.moko.permissions.PermissionsController
import nick.mirosh.newsapp.di.androidModule
import nick.mirosh.newsappkmp.di.KoinContainer
import nick.mirosh.newsappkmp.ui.HomeNavigation
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KoinContainer.initKoin(
            platformSpecificModule = androidModule
        ) {
            androidContext(applicationContext)
        }
        //eagerly initialize PermissionsController
        get<PermissionsController> { parametersOf(this@MainActivity) }

        setContent {
            HomeNavigation()
        }
    }
}






