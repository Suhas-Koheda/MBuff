# MovieBuff - Cross-Platform Movie Browser

MovieBuff is a modern cross-platform movie browsing application built with Kotlin Multiplatform and Jetpack Compose Multiplatform. This application allows users to discover, search, and view detailed information about movies from The Movie Database (TMDB) API across Android, iOS, and Desktop platforms.

## 📱 Features

- **Cross-Platform Support**: Single codebase that runs on Android, iOS, and Desktop
- **Movie Discovery**: Browse TMDB movies with pagination support
- **Multi-Language Support**: Filter movies in 13 different languages:
  - Telugu, English, Hindi, Bengali, Tamil
  - Marathi, Gujarati, Kannada, Malayalam
  - Punjabi, Odia, Assamese, Urdu
- **Search Functionality**: Find movies by title with real-time results
- **Detailed Movie Views**: View comprehensive details including synopsis, ratings, release date, etc.
- **Cached Responses**: Optimized performance with response caching
- **Responsive Design**: Adapts to different screen sizes and orientations

## 🏗️ Architecture

The project follows a clean architecture approach with clear separation of concerns:

### 📂 Project Structure

```
MovieBuff/
├── composeApp/                 # Shared code for all platforms
│   ├── src/
│   │   ├── androidMain/        # Android-specific code
│   │   ├── commonMain/         # Shared code
│   │   │   └── kotlin/
│   │   │       └── dev/haas/mobuff/
│   │   │           ├── App.kt  # Main app entry point
│   │   │           └── movies/ # Movie browsing features
│   │   │               ├── data/        # Data layer
│   │   │               │   ├── interfaces/  # API interfaces
│   │   │               │   ├── local/       # Local storage 
│   │   │               │   └── repository/  # Data repositories
│   │   │               ├── domain/      # Domain models
│   │   │               └── presentation/ # UI layer
│   │   │                   ├── ui/       # Compose UI components
│   │   │                   └── viewmodel/ # ViewModels
│   │   ├── desktopMain/        # Desktop-specific code
│   │   └── iosMain/            # iOS-specific code
├── iosApp/                     # iOS app configuration
└── gradle/                     # Gradle configuration
```

## 🛠️ Technology Stack

### Core Libraries

| Library | Version | Purpose |
|---------|---------|---------|
| Kotlin Multiplatform | 2.1.20 | Cross-platform code sharing |
| Compose Multiplatform | 1.7.3 | UI framework for all platforms |
| Ktor | 3.1.2 | HTTP networking with TMDB API |
| Kotlinx Serialization | 1.8.0 | JSON parsing and serialization |
| Voyager | 1.1.0-beta02 | Navigation between screens |
| Coil | 3.1.0 | Image loading and caching |
| Cache4k | 0.14.0 | In-memory caching of API responses |
| Room (Android) | 2.7.1 | Local database (prepared but not fully implemented) |
| Lifecycle Components | 2.8.4 | ViewModel and lifecycle management |

## 🧩 Key Components

### Data Layer

- **TMDBClient**: Interface defining API endpoints and HTTP client configuration
- **TMDBRepository**: Implements the API client with caching for optimized performance
- **MovieEntity**: Data model for local storage (prepared for future implementation)

### Domain Layer

- **Movie**: Core data model representing movie information with computed properties for image URLs
- **MovieResponse**: Data wrapper for API responses with pagination information

### Presentation Layer

- **MovieViewModel**: Manages UI state and data fetching operations with coroutines
- **MovieScreen**: Main UI component for displaying the movie grid with search and filter options
- **MovieDetailScreen**: Detailed view of a selected movie with animations and expandable sections
- **App**: Main composition entry point that sets up navigation

## 📊 Key Functions

| Function | Purpose |
|----------|---------|
| `fetchMovies(query, language, page)` | Loads movies from the API with optional filters |
| `getMovie(query, language, page)` | Repository function to fetch movies with caching |
| `MovieItem` | Composable function to render a movie card in the grid |
| `SearchWidget` | Composable function for the search interface |
| `Filters` | Composable function for language and pagination filters |
| `MovieDetailScreen` | Composable function for the detailed movie view |
| `Overview` & `Details` | Expandable sections in the movie detail view |

## 📝 Recent Development Activity

Recent commits show active development including:

- Cache implementation using Cache4k for improved performance
- UI and ViewModel refactoring for better separation of concerns
- Documentation improvements and code structure refinements
- Hot reload functionality for faster development workflow
- Data layer migration and architecture improvements

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or newer / IntelliJ IDEA
- JDK 11 or newer
- Xcode 14+ (for iOS builds)

### Running the Application

#### Android
```bash
./gradlew :composeApp:installDebug
```

#### Desktop
```bash
./gradlew :composeApp:run
```

#### iOS
```bash
./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
```
Then open the `iosApp/iosApp.xcodeproj` in Xcode and run the project.

## 👥 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 🙏 Acknowledgements

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for providing the movie data API
- [JetBrains](https://www.jetbrains.com/) for Kotlin Multiplatform and tools
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) for cross-platform UI framework
