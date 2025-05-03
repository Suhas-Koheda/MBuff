# MovieBuff - Kotlin Multiplatform Compose Project

MovieBuff is a Kotlin Multiplatform project that uses Jetpack Compose Multiplatform to create a shared UI for Android, iOS, and Desktop platforms. It demonstrates how to build a movie browsing application with a shared codebase.

## Project Structure

The project is organized as follows:

- **`/composeApp`**: Contains the shared code for the Compose Multiplatform application.
  - **`commonMain`**: Code shared across all platforms (UI, business logic, etc.).
  - **`androidMain`**, **`iosMain`**, **`desktopMain`**: Platform-specific code for Android, iOS, and Desktop, respectively.

- **`/iosApp`**: Entry point for the iOS application. This folder contains the iOS-specific setup and SwiftUI integration.

- **`/androidApp`**: Entry point for the Android application. This folder contains the Android-specific setup.

## Running the Application

### Prerequisites

1. Install [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) in IntelliJ IDEA or Android Studio.
2. Install the required SDKs for Android, iOS, and Desktop development:
   - Android: Install Android Studio and configure the Android SDK.
   - iOS: Install Xcode and configure the iOS SDK.
   - Desktop: Ensure you have Java 11 or higher installed.

---

### Running on Android

1. Open the project in Android Studio.
2. Select the `androidApp` configuration.
3. Connect an Android device or start an Android emulator.
4. Click the "Run" button to build and deploy the app to the Android device/emulator.

---

### Running on iOS

1. Open the project in Xcode by navigating to the `iosApp` folder and opening the `.xcworkspace` file.
2. Select a simulator or connect a physical iOS device.
3. Click the "Run" button to build and deploy the app to the iOS device/simulator.

---

### Running on Desktop

1. Open a terminal and navigate to the project root directory.
2. Run the following command to build and run the desktop application:
   ```bash
   ./gradlew build :composeApp:run
   ```
3. The desktop application will launch in a new window.

---

## Features

- **Movie Browsing**: Browse movies by language, popularity, and release date.
- **Search**: Search for movies by title.
- **Details View**: View detailed information about a selected movie.

## Learn More

- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Jetpack Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [The Movie Database (TMDb) API](https://developers.themoviedb.org/3)

## License

This project is licensed under the MIT License. See the LICENSE file for details.
