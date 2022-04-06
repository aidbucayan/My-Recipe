# My-Recipe
![Screenshot_20220405_143752](https://user-images.githubusercontent.com/15811376/161696668-1d40b6b2-2ae2-44c3-adba-c1d8194fd485.png)

Android project written in Kotlin which demonstrates MVVM/MVI design architecture.

- Retrieve data from an api with Retrofit
- Display data in UI.

Design Pattern
The project used MVI and Repository design pattern approach. State in app is defined by user's action which is called intent (not the android Intent class) which the ViewModel will get and decide the state to be reflected to the View. Intent represents an intention or a desire to perform an action, either by the user or the app itself. For every action, a View receives an Intent. The Presenter observes the Intent, and Models translate it into a new state.

## Libraries
- [Jetpack Hilt](https://dagger.dev/hilt/) - Dependency injection
- [Retrofit](https://square.github.io/retrofit/)  - API http network requests.
- [OkHttp](https://square.github.io/okhttp/) - Use as http client for logging interceptor.
- [Jackson](https://github.com/square/retrofit/tree/master/retrofit-converters/jackson) - JSON serialization.
- [Timber](https://github.com/JakeWharton/timber) - Logging and crash reports.
- [Glide](https://github.com/bumptech/glide) - Image loader to views.
- [Material](https://material.io/) Design - Google's material design ui.                                                                                               

- [Coutine Flow] (https://developer.android.com/kotlin/flow) - In coroutines, a flow is a type that can emit multiple values sequentially 
                           
## Setup
Run the following command to build the project
```sh
./gradlew assembleDevDebug
```
or

./gradlew assembleProdRelease
Installation
Installing apk to device can be done with the following commands. Note that debug apk is used in the commands.

via Gradle
```sh
./gradlew installDevDebug
```
via adb tool
adb install build/outputs/apk/debug/MyRecipe_dev_debug_1.0.apk
Linting
Lint issues or warnings can be checked by running
```sh
./gradlew lint
```
Future Updates: 
- Room Database for data caching
- Jetpack compose for UI 
