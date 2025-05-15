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
- **Theme Toggling**: Light and dark mode support with Material3 theming

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
│   │   │   └── kotlin/
│   │   │       └── dev/haas/mobuff/
│   │   │           └── MainActivity.kt    # Android entry point
│   │   ├── commonMain/         # Shared code
│   │   │   └── kotlin/
│   │   │       └── dev/haas/mobuff/
│   │   │           ├── App.kt  # Main app entry point
│   │   │           └── movies/ # Movie browsing features
│   │   │               ├── data/        # Data layer
│   │   │               │   ├── interfaces/  # API interfaces (TMDBClient)
│   │   │               │   ├── local/       # Local storage (MovieEntity)
│   │   │               │   └── repository/  # Data repositories (TMDBRepository)
│   │   │               ├── domain/      # Domain models
│   │   │               │   └── model/   # Data classes (Movie, MovieResponse)
│   │   │               └── presentation/ # UI layer
│   │   │                   ├── components/ # Reusable UI components
│   │   │                   │   ├── FilterComponent.kt    # Language & pagination filters
│   │   │                   │   ├── MovieGrid.kt          # Grid layout for movies
│   │   │                   │   ├── MovieItem.kt          # Individual movie card
│   │   │                   │   ├── SearchWidget.kt       # Search input component
│   │   │                   │   └── ThemeToggle.kt        # Light/dark theme toggle
│   │   │                   ├── theme/    # Theme definitions
│   │   │                   │   └── MobuffTheme.kt        # Material3 theme with light/dark modes
│   │   │                   ├── ui/       # Screen implementations
│   │   │                   │   ├── MovieView.kt          # Main movie browsing screen
│   │   │                   │   └── MovieDetailScreen.kt  # Detailed movie view
│   │   │                   └── viewmodel/ # ViewModels
│   │   │                       └── MovieViewModel.kt     # Manages movie data and UI state
│   │   ├── desktopMain/        # Desktop-specific code
│   │   │   └── kotlin/
│   │   │       └── dev/haas/mobuff/
│   │   │           └── Main.kt            # Desktop entry point
│   │   └── iosMain/            # iOS-specific code
│   │       └── kotlin/
│   │           └── dev/haas/mobuff/
│   │               └── MainViewController.kt # iOS entry point
├── iosApp/                     # iOS app configuration
└── gradle/                     # Gradle configuration
```

## 🛠️ Technology Stack

### Core Libraries

| Library | Version | Purpose in MovieBuff |
|---------|---------|---------|
| Kotlin Multiplatform | 2.1.20 | Foundation for sharing code across platforms with platform-specific implementations where needed |
| Compose Multiplatform | 1.7.0+ | Powers the UI layer with a declarative approach across all supported platforms |
| Ktor Client | 3.x | Handles HTTP networking with TMDB API with content negotiation and retry capabilities |
| Kotlinx Serialization | 2.1.20 | Parses JSON responses from TMDB API into Kotlin data classes |
| Voyager | 1.x | Manages navigation between movie list and detail screens with state preservation |
| Coil | 3.x | Loads and caches movie poster and backdrop images from TMDB CDN |
| Cache4k | 0.14.0 | Provides in-memory caching of API responses to improve performance and reduce API calls |
| Room (Android) | 2.x | Prepared for future implementation of local database storage |
| Lifecycle Components | 2.x | Manages ViewModels and their lifecycles across the application |
| Material3 | 1.x | Provides modern UI components and theming capabilities |
| Material Icons | 1.7.0 | Icons for the application UI elements |

### Development Tools

| Tool | Version | Purpose in MovieBuff |
|------|---------|---------|
| Gradle KMP Plugin | 2.1.20 | Configures Kotlin Multiplatform build process |
| Android Gradle Plugin | Latest | Configures Android-specific build settings |
| Compose Compiler | Latest | Optimizes Compose UI compilation |
| KSP | 2.1.20-1.0.32 | Kotlin Symbol Processing for annotation processing |
| Compose Hot Reload | Latest | Speeds up development by allowing UI changes without full rebuilds |
| Kotlin Serialization Plugin | 2.1.20 | Generates serialization code for data classes |

## 🧩 Key Components in Detail

### Data Layer

- **TMDBClient**: 
  - Interface defining the API contract with endpoint URLs and HTTP client configuration
  - Sets up Ktor HTTP client with content negotiation, JSON parsing, and retry logic
  - Defines the `getMovie()` method signature for fetching movies from TMDB
  - Constants for API endpoints (`SEARCHURL`, `LISTURL`)
  - Configured with proper error handling and retry mechanisms

- **TMDBRepository**: 
  - Implements the TMDBClient interface with a singleton pattern accessible via `instance` property
  - Manages in-memory caching with Cache4k based on query/language/page combinations
  - Creates cache keys using the format `"$query-$language-$page"` for efficient retrieval
  - Handles API requests with proper parameter formatting and authentication
  - Uses a bearer token for TMDB API authentication
  - Implements conditional URL selection between search and discovery endpoints

- **MovieEntity**: 
  - Prepared data model for future local storage implementation
  - Structured for Room database integration when offline support is added

### Domain Layer

- **Movie**: 
  - Core data model with all movie properties (title, overview, release date, etc.)
  - Contains computed properties for poster and backdrop image URLs:
    - `posterPathUrl`: Formats TMDB poster path or provides fallback image
    - `backDropPathUrl`: Formats TMDB backdrop path or returns empty string
  - Implements Serializable for JSON parsing compatibility
  - Handles null safety for optional fields like release date and image paths

- **MovieResponse**: 
  - Wrapper for API response containing movie results and pagination info
  - Captures `page`, `results` (list of movies), `totalPages`, and `totalResults`
  - Helps handle the TMDB API's pagination model
  - Makes `totalPages` and `totalResults` optional with nullable types

### Presentation Layer

- **MovieViewModel**: 
  - Uses sealed class for representing UI state:
    - `Loading`: Indicates data is being fetched
    - `Success`: Contains the fetched movie list
    - `Error`: Contains error message when fetch fails
  - Manages coroutine-based data fetching with viewModelScope
  - Handles error cases and provides appropriate messages
  - Exposes state as a StateFlow for reactive UI updates
  - Provides `fetchMovies()` function with default parameters

- **MovieScreen**: 
  - Main screen displaying the movie grid with search and filter options
  - Persists user selections (query, language, page) across navigation using companion object
  - Manages state transitions between loading, success, and error states
  - Uses Scaffold for structured layout with proper Material Design principles
  - Integrates with Voyager navigator for navigation handling

- **MovieDetailScreen**: 
  - Presents detailed view of a selected movie
  - Features animations with AnimatedVisibility for expandable sections
  - Displays backdrop image with proper scaling and content description
  - Implements collapsible sections for overview and details
  - Uses Material Design 3 components for modern UI appearance
  - Formats movie information with proper typography hierarchy

- **UI Components**:
  - **MovieGrid**: 
    - Implements LazyVerticalGrid with adaptive columns (minimum 128dp)
    - Handles proper padding and item spacing
    - Passes click handling to parent components
  
  - **MovieItem**: 
    - Card-based UI component with poster image loaded via AsyncImage (Coil)
    - Implements proper aspect ratio (2:3) for movie posters
    - Uses gradients for text legibility over images
    - Applies proper clipping and corner rounding

  - **SearchWidget**: 
    - Text input field with state management
    - Implements clear button that appears when text is entered
    - Provides callback for search query changes
    - Uses proper Material Design text field styling

  - **Filters**: 
    - Contains 13 Indian language options with proper selection indicators
    - Implements pagination controls with 10 page buttons plus "Next" option
    - Uses ElevatedFilterChip components for consistent UI
    - Properly manages state with callbacks to parent components

  - **ThemeToggleButton**:
    - Toggles between light and dark themes
    - Uses IconButton with appropriate Material icons

## 📊 Key Functions and Their Implementation Details

| Function | Location | Implementation Details |
|----------|----------|------------------------|
| `fetchMovies(query, language, page)` | MovieViewModel.kt | Uses viewModelScope to launch a coroutine, updates loading state, calls repository, handles exceptions with Error state |
| `getMovie(query, language, page)` | TMDBRepository.kt | Checks cache first with generated key, builds API request with parameters if cache miss, stores response in cache for future requests |
| `MovieGrid(moviesList, onClick)` | MovieGrid.kt | Implements a LazyVerticalGrid with adaptive column sizes (GridCells.Adaptive), applies proper content padding and item spacing |
| `MovieItem(movie, onClick)` | MovieItem.kt | Card-based UI component with poster image loading via Coil's AsyncImage and proper click handling, applies visual styling for consistent appearance |
| `SearchWidget(initialQuery, onQueryChanged)` | SearchWidget.kt | Implements a text input field with clear button and search query callback, uses proper Material Design styling |
| `Filters(initialLanguage, initialPage, onLanguageSelected, onPageSelected)` | FilterComponent.kt | Displays language options and pagination controls using Material3 chips, manages state with callbacks to parent components |
| `ThemeToggleButton()` | ThemeToggle.kt | Implements a toggle button for switching between light and dark themes using Material3 IconButton |

## 🚀 Getting Started

### Prerequisites
- JDK 11 or newer
- Android Studio Arctic Fox or newer (for Android development)
- Xcode 14 or newer (for iOS development)
- IntelliJ IDEA or Visual Studio Code (for desktop development)

### Building the Project
1. Clone the repository:
```bash
git clone https://github.com/yourusername/mobuff.git
cd mobuff
```

2. Open the project in your IDE of choice.

3. Build and run the application:
   - For Android: Run the `androidApp` configuration.
   - For Desktop: Run the `desktopApp` configuration.
   - For iOS: Open the Xcode project in the `iosApp` directory and run it.

### API Key Configuration
This project uses The Movie Database (TMDB) API. An API key is already included for demo purposes, but for production use, replace it with your own key in `TMDBRepository.kt`.

## 📝 Git Commit History Overview

The project has been developed through multiple iterations focusing on different aspects:

1. **Initial Setup** - Project structure creation with Kotlin Multiplatform and Compose
2. **API Integration** - Implementation of TMDB API client and repository
3. **UI Components** - Development of core UI components like MovieGrid and MovieItem
4. **Search & Filtering** - Addition of search functionality and language filtering
5. **Theming Support** - Implementation of light/dark theme toggle
6. **Caching Layer** - Integration of Cache4k for response caching
7. **Navigation** - Implementation of Voyager navigator for screen transitions
8. **Performance Optimizations** - Improvements to rendering and data loading
9. **State Persistence** - Added saved state for user preferences
10. **Final Refinements** - UI polish and code cleanup

## 🔍 Detailed Function Implementations

### Core Functions

| Function | Implementation Details | Usage Example |
|----------|------------------------|---------------|
| `fetchMovies()` | Uses coroutines with error handling and state management | `movieViewModel.fetchMovies(query = "Avengers", language = "en", page = 1)` |
| `getMovie()` | Implements caching with conditional URL selection based on query presence | `tmdbRepository.getMovie(query = "", language = "hi", page = 2)` |
| `MovieItem()` | Creates visually appealing card with proper image handling | `MovieItem(movie = movieData) { navigateToDetails(it) }` |
| `SearchWidget()` | Features debounced search with animation for clear button | `SearchWidget(initialQuery = "") { query -> updateResults(query) }` |
| `Filters()` | Displays language options and pagination using Material3 chips | `Filters(initialLanguage = "te", initialPage = 1, languageFun = { ... }, pageFun = { ... })` |
| `ThemeToggleButton()` | Implements Material3 IconButton with appropriate icons for theme switching | `ThemeToggleButton()` |

### Movie Screen Lifecycle

The main `MovieScreen` implements several key behaviors:
1. Initializes `MovieViewModel` with repository dependency
2. Restores saved state (query, language, page) from companion object
3. Renders UI components with proper state handling
4. Observes state changes via `collectAsState()`
5. Handles navigation to detail screen when a movie is selected

### Cache Implementation

The caching mechanism in `TMDBRepository` follows these steps:
1. Creates a unique cache key using query, language, and page parameters
2. Checks if a cached response exists with that key
3. Returns cached data if available
4. Otherwise makes a network request and caches the result
5. Cache is implemented using Cache4k with in-memory storage

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
