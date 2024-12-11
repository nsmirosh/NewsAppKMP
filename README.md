# Clean Architecture MVVM Compose Multiplatform News App

### App description

Disclaimer: 

This app is used for my own learning and is a WIP - do not expect everything to work here perfectly.
The Compose UI for iOS and Room database are still in alpha - so don't expect them to work perfectly either.

This is a simple app that displays a list of news articles from [Newsdata.io](https://newsdata.io/).
It has the ability to preview the articles in a WebView and save them to favorites.
The intent here is to try to share **as much as possible between Android and iOS**.

This is a Compose Multiplatform adaptation of the [Android News App](https://github.com/nsmirosh/NewsApp). 

https://github.com/user-attachments/assets/74e79d93-0e9a-4778-8dfe-6c8dd3e872fa

### Set up:

#### To run the app:
1. Get your API key at [Newsdata.io](https://newsdata.io/)
2. Create a local.properties file in the root of the project.
3. Add a line `API_KEY=your_newsdata.io_key_here`

#### Technologies used

- Kotlin
- Compose
- MVVM 
- Coroutines, Flows
- Room
- Native Location services
- Image loading using [Coil](https://coil-kt.github.io/coil/)
- Dependency injection via [Koin](https://insert-koin.io/)
- Networking using [Ktor](https://ktor.io/)
- Navigation and state management using [Voyager](https://voyager.adriel.cafe/)
- Date formatting using [Kotlinx DateTime](https://github.com/Kotlin/kotlinx-datetime)
- Permission handling using [Moko](https://github.com/icerockdev/moko-permissions)
- Logging via [Kermit](https://kermit.touchlab.co/)
- Key- Value storage using [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)

Planned: 
- Tests
- Custom UI per platform

Design inspired by [Tomas Nozina](https://dribbble.com/shots/15246621-Denn-k-N-News-App)
