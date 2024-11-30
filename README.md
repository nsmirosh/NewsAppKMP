# Clean Architecture MVVM Compose Multiplatform News App

### App description

Disclaimer: 

This app is used for my own learning and is a WIP - do not expect everything to work here perfectly.
The Compose UI for iOS and Room database are still in alpha - so don't expect them to work perfectly either.

This is a simple app that displays a list of news article from [NewsAPI.org](https://newsapi.org/).
It has the ability to preview the articles in a WebView and save them to favorites.
The intent here is to try to share **as much as possible between Android and iOS**.

This is a Compose Multiplatform adaptation of the [Android News App](https://github.com/nsmirosh/NewsApp). 

### Set up:

#### To run the app:
1. Get your API key at [NewsAPI.org](https://newsapi.org/)
2. Create a local.properties file in the root of the project.
3. Add a line `API_KEY=your_newsapi.org_key_here`

#### Technologies used

- Kotlin
- Compose
- MVVM Clean Architecture
- Koin
- Coroutines
- Ktor
- Navigation using [Voyager](https://voyager.adriel.cafe/)
- Date formatting using [Kotlinx DateTime](https://github.com/Kotlin/kotlinx-datetime)

Planned: 

- Room
- Tests
- Sensor Managemenent
- Permission handling

https://github.com/user-attachments/assets/d3500853-39f8-4a41-8c81-85a2734dfad3


Design inspired by [Tomas Nozina](https://dribbble.com/shots/15246621-Denn-k-N-News-App)
