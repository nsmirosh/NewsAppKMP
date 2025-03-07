package nick.mirosh.newsappkmp.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

object KoinContainer {

    private var initialized = false

    fun initKoin(
        platformSpecificModule: Module,
        appDeclaration: KoinAppDeclaration = {},
    ): KoinApplication? {
        var koinApplication: KoinApplication? = null

        //TODO Hack to avoid multiple Koin initialization in iOS
        if (!initialized) {
            initialized = true

            koinApplication = startKoin {
                appDeclaration()
                modules(
                    platformSpecificModule,
                    appModule,
                    dataModule,
                    networkModule,
                    repositoryModule,
                    dispatchersModule
                )
            }
        }
        return koinApplication
    }
}
