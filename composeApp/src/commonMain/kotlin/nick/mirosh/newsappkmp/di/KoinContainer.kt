package nick.mirosh.newsappkmp.di

import nick.mirosh.newsapp.di.networkModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object KoinContainer {

    private var initialized = false

    fun initKoin(
        enableNetworkLogs: Boolean = false,
        appDeclaration: KoinAppDeclaration = {}
    ): KoinApplication? {
        var koinApplication: KoinApplication? = null

        //TODO Hack to avoid multiple Koin initialization in iOS
        if (!initialized) {
            initialized = true

            koinApplication = startKoin {
                appDeclaration()
                modules(appModule, networkModule)
            }
        }
        return koinApplication
    }
}
