# MovieBuff - Cross-Platform Movie Browser

MovieBuff is a modern cross-platform movie browsing application built with Kotlin Multiplatform and Jetpack Compose Multiplatform. This application allows users to discover, search, and view detailed information about movies from The Movie Database (TMDB) API across Android, iOS, and Desktop platforms.

## 📱 Features

- **Cross-Platform Support**: Single codebase that runs on Android, iOS, and Desktop
- **Movie Discovery**: Browse TMDB movies with pagination support (up to 10 pages)
- **Multi-Language Support**: Filter movies in 13 different Indian languages:
  - Telugu, English, Hindi, Bengali, Tamil
  - Marathi, Gujarati, Kannada, Malayalam
  - Punjabi, Odia, Assamese, Urdu
- **Search Functionality**: Find movies by title with real-time results
- **Detailed Movie Views**: View comprehensive details including synopsis, ratings, release date, etc.
- **Cached Responses**: Optimized performance with in-memory response caching
- **Responsive Design**: Adapts to different screen sizes and orientations
- **State Persistence**: Remembers your last search, language, and page selections

## 🏗️ Architecture

The project follows a clean architecture approach with clear separation of concerns across three main layers:

- **Data Layer**: Handles API communication, caching, and data persistence
- **Domain Layer**: Contains business logic and domain models
- **Presentation Layer**: Manages UI state and user interactions

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
│   │   │               │   ├── interfaces/  # API interfaces (TMDBClient)
│   │   │               │   ├── local/       # Local storage (MovieEntity)
│   │   │               │   └── repository/  # Data repositories (TMDBRepository)
│   │   │               ├── domain/      # Domain models (Movie, MovieResponse)
│   │   │               └── presentation/ # UI layer
│   │   │                   ├── components/ # Reusable UI components
│   │   │                   ├── ui/       # Screen implementations
│   │   │                   └── viewmodel/ # ViewModels (MovieViewModel)
│   │   ├── desktopMain/        # Desktop-specific code
│   │   └── iosMain/            # iOS-specific code
├── iosApp/                     # iOS app configuration
└── gradle/                     # Gradle configuration
```

## 🛠️ Technology Stack

### Core Libraries

| Library | Version | Purpose in MovieBuff |
|---------|---------|---------|
| Kotlin Multiplatform | 2.1.20 | Foundation for sharing code across platforms with platform-specific implementations where needed |
| Compose Multiplatform | 1.7.3 | Powers the UI layer with a declarative approach across all supported platforms |
| Ktor | 3.1.2 | Handles HTTP networking with TMDB API with content negotiation and retry capabilities |
| Kotlinx Serialization | 1.8.0 | Parses JSON responses from TMDB API into Kotlin data classes |
| Voyager | 1.1.0-beta02 | Manages navigation between movie list and detail screens with state preservation |
| Coil | 3.1.0 | Loads and caches movie poster and backdrop images from TMDB CDN |
| Cache4k | 0.14.0 | Provides in-memory caching of API responses to improve performance and reduce API calls |
| Room (Android) | 2.7.1 | Prepared for future implementation of local database storage |
| Lifecycle Components | 2.8.4 | Manages ViewModels and their lifecycles across the application |

### Development Tools

| Tool | Purpose in MovieBuff |
|------|---------|
| Gradle | Build system with KMP plugin configuration |
| KSP | Kotlin Symbol Processing for annotation processing |
| Compose Hot Reload | Speeds up development by allowing UI changes without full rebuilds |
| Kotlin Serialization Plugin | Generates serialization code for data classes |

## 🧩 Key Components in Detail

### Data Layer

- **TMDBClient**: 
  - Interface defining the API contract with endpoint URLs and HTTP client configuration
  - Sets up Ktor HTTP client with content negotiation, JSON parsing, and retry logic
  - Defines the `getMovie()` method signature for fetching movies from TMDB

- **TMDBRepository**: 
  - Implements the TMDBClient interface with a singleton pattern
  - Manages in-memory caching with Cache4k based on query/language/page combinations
  - Handles API requests with proper parameter formatting and authentication
  - Uses a bearer token for TMDB API authentication

- **MovieEntity**: 
  - Prepared data model for future local storage implementation
  - Will be expanded for Room database integration

### Domain Layer

- **Movie**: 
  - Core data model with all movie properties (title, overview, release date, etc.)
  - Contains computed properties for poster and backdrop image URLs
  - Handles fallback image URL when movie poster is not available
  - Serializable for JSON parsing

- **MovieResponse**: 
  - Wrapper for API response containing movie results and pagination info
  - Helps handle the TMDB API's pagination model

### Presentation Layer

- **MovieViewModel**: 
  - Uses sealed class for representing UI state (Loading, Success, Error)
  - Manages coroutine-based data fetching with viewModelScope
  - Handles error cases and provides appropriate messages
  - Exposes state as a StateFlow for reactive UI updates

- **MovieScreen**: 
  - Main screen displaying the movie grid with search and filter options
  - Persists user selections (query, language, page) across navigation
  - Manages state transitions between loading, success, and error states

- **MovieDetailScreen**: 
  - Presents detailed view of a selected movie
  - Features animations and expandable sections for overview and details
  - Uses Material Design 3 components for modern UI appearance

- **UI Components**:
  - **MovieGrid**: Displays movies in a responsive grid layout
  - **MovieItem**: Individual movie card with poster image and basic info
  - **SearchWidget**: Search input with suggestions and submit functionality
  - **Filters**: Language selection and pagination controls

## 📊 Key Functions and Their Implementation Details

| Function | Location | Implementation Details |
|----------|----------|------------------------|
| `fetchMovies(query, language, page)` | MovieViewModel | Uses viewModelScope to launch a coroutine, updates loading state, calls repository, handles exceptions |
| `getMovie(query, language, page)` | TMDBRepository | Checks cache first, builds API request with parameters if cache miss, stores response in cache |
| `MovieGrid(moviesList, onClick)` | MovieGrid.kt | Implements a LazyVerticalGrid with adaptive column sizes and padding for responsive layouts |
| `MovieItem(movie, onClick)` | MovieItem.kt | Card-based UI component with poster image loading via Coil and click handling |
| `SearchWidget(initialQuery, pref)` | SearchWidget.kt | Text input field with state management and submit button for queries |
| `Filters(languageFun, pageFun)` | FilterComponent.kt | Row of language FilterChips and pagination controls with state callbacks |
| `MovieDetailScreen(movie)` | MovieDetailScreen.kt | Complex screen with backdrop image, expandable sections, and formatted movie details |
| `Overview(movie)` & `Details(movie)` | MovieDetailScreen.kt | Expandable card sections for movie synopsis and additional information |

## 📝 Recent Development Activity

Recent commits show active development including:

- **Cache Implementation**: Added Cache4k integration for improved performance and reduced API calls
- **UI and ViewModel Refactoring**: Better separation of concerns with dedicated components
- **State Management**: Improved state persistence across screen navigation
- **Error Handling**: Enhanced error states and user feedback throughout the app
- **Navigation**: Implemented Voyager for simplified navigation between screens
- **Documentation Updates**: Added comprehensive comments and README refinements
- **Hot Reload Integration**: Added Compose Hot Reload for faster development workflow
- **Data Layer Migration**: Prepared groundwork for local storage with Room
- **API Authentication**: Updated to use bearer token authentication with TMDB API
- **Responsive Design**: Improved layout adaptations for various screen sizes

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or newer / IntelliJ IDEA
- JDK 11 or newer
- Xcode 14+ (for iOS builds)
- TMDB API key (register at [themoviedb.org](https://www.themoviedb.org/))

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

### Future Development Ideas

- Offline support with Room database implementation
- User watchlist and favorites functionality
- Movie recommendations based on viewing history
- UI themes (light/dark mode)
- More detailed actor and crew information
- Video trailer integration
- User ratings and reviews

## 📄 License

This project is licensed under the MIT License.

## 🙏 Acknowledgements

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for providing the movie data API
- [JetBrains](https://www.jetbrains.com/) for Kotlin Multiplatform and tools
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) for cross-platform UI framework
- [Ktor](https://ktor.io/) for the powerful HTTP client
- [Coil](https://coil-kt.github.io/coil/) for image loading capabilities
- [Voyager](https://voyager.adriel.cafe/) for navigation components
- [Cache4k](https://github.com/ReactiveCircus/cache4k) for in-memory caching solution
