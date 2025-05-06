# MovieBuff - Cross-Platform Movie Browser

![MovieBuff Logo](https://via.placeholder.com/800x200?text=MovieBuff)

MovieBuff is a modern, cross-platform movie browsing application built with Kotlin Multiplatform and Jetpack Compose Multiplatform. This project demonstrates how to create a shared codebase that runs seamlessly on Android, iOS, and Desktop platforms.

## ✨ Features

- **Cross-Platform UI**: Single codebase for Android, iOS, and Desktop platforms
- **Movie Discovery**: Browse popular movies from The Movie Database (TMDB)
- **Search Functionality**: Find movies by title
- **Detailed Movie Information**: View comprehensive details about each movie
- **Pagination Support**: Navigate through multiple pages of movie listings
- **Language Filtering**: Filter movies by 13 different languages including:
  - Telugu, English, Hindi, Bengali, Tamil
  - Marathi, Gujarati, Kannada, Malayalam
  - Punjabi, Odia, Assamese, Urdu

## 🏗️ Architecture & Technology Stack

### Architecture

The project follows a clean architecture approach with:
- **Presentation Layer**: Compose UI components and ViewModels
- **Domain Layer**: Business logic and models
- **Data Layer**: Repositories and data sources

### Technologies Used

- **Kotlin Multiplatform**: For shared code across platforms
- **Jetpack Compose Multiplatform**: For shared UI code
- **Ktor**: For HTTP network requests to TMDB API
- **Kotlinx Serialization**: For JSON parsing
- **Voyager**: For navigation between screens
- **Coil**: For image loading and caching
- **Material 3**: For modern UI components and theming

## 🚀 Project Structure

```
MovieBuff/
├── composeApp/                 # Shared code for all platforms
│   ├── src/
│   │   ├── androidMain/        # Android-specific code
│   │   ├── commonMain/         # Shared code across platforms
│   │   │   └── kotlin/
│   │   │       └── dev/haas/mobuff/
│   │   │           ├── App.kt  # Main app entry point
│   │   │           └── movies/ # Movie browsing features
│   │   │               ├── data/       # Data layer with repositories
│   │   │               ├── domain/     # Domain models
│   │   │               └── presentation/ # UI components and ViewModels
│   │   ├── desktopMain/        # Desktop-specific code
│   │   └── iosMain/            # iOS-specific code
├── iosApp/                     # iOS app setup
└── gradle/                     # Gradle configuration
```

## 📱 Screenshots

<table>
  <tr>
    <td><img src="https://via.placeholder.com/250x500?text=Movie+List" alt="Movie List" /></td>
    <td><img src="https://via.placeholder.com/250x500?text=Movie+Details" alt="Movie Details" /></td>
    <td><img src="https://via.placeholder.com/250x500?text=Search" alt="Search" /></td>
  </tr>
</table>

## 🛠️ Setup and Installation

### Prerequisites

- [Android Studio](https://developer.android.com/studio) or [IntelliJ IDEA](https://www.jetbrains.com/idea/) with Kotlin Multiplatform Mobile plugin
- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or newer
- [Xcode](https://developer.apple.com/xcode/) (for iOS builds)
- [TMDB API Key](https://developers.themoviedb.org/3/getting-started/introduction) (already configured in the repository)

### Building the Project

#### Android

1. Open the project in Android Studio
2. Select the 'composeApp' configuration
3. Click 'Run' to build and run on an Android device or emulator

#### iOS

1. Open the project in Android Studio
2. Select "Run on iOS device" or "Run on iOS simulator" configuration
3. Alternatively, open `/iosApp/iosApp.xcodeproj` in Xcode and run from there

#### Desktop

1. Open the project in Android Studio or IntelliJ IDEA
2. Select the 'desktopApp' configuration
3. Click 'Run' to build and run on your desktop

Alternatively, run from the command line:
```bash
./gradlew :composeApp:run
```

## 🤝 Contributing

Contributions are welcome! Feel free to:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgements

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for the movie data API
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the modern UI toolkit
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) for cross-platform development
