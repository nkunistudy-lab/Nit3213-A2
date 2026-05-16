# NIT3213 Final Assignment - Android App

## Overview
An Android application that authenticates users via the VU NIT3213 API and displays a dashboard of entities retrieved from the server. Users can tap any entity to view its full details.

## Features
- **Login Screen** — Authenticates using POST request to `/sydney/auth`
- **Dashboard Screen** — Displays entities in a RecyclerView from `/dashboard/{keypass}`
- **Details Screen** — Shows full entity information including description

## Architecture
The app follows Clean Architecture principles with the following layers:
- **UI Layer** — Fragments observe StateFlow from ViewModels
- **ViewModel Layer** — Manages UI state using Kotlin Coroutines and StateFlow
- **Repository Layer** — Single source of truth for data operations
- **Network Layer** — Retrofit with Moshi for API communication

## Tech Stack
- **Language:** Kotlin
- **UI:** Android Views (XML) with Material Design components
- **Networking:** Retrofit 2 + Moshi + OkHttp
- **Async:** Kotlin Coroutines + StateFlow
- **DI:** Hilt (Dagger)
- **Navigation:** Jetpack Navigation Component
- **Testing:** JUnit 4 + MockK
- **Version Control:** Git

## Build and Run
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Run the app on an emulator or physical device (minimum API 24)

## Login Credentials
- **Username:** s8119116
- **Password:** Nischal
- **Endpoint:** /sydney/auth

## Running Tests
- In Android Studio: Right-click the `test` folder → **Run Tests**
- Or via terminal: `./gradlew test`

## Project Structure
```
com.example.nit3213app/
├── MyApplication.kt          # Hilt Application class
├── MainActivity.kt            # Host Activity with NavHostFragment
├── di/
│   └── AppModule.kt           # Hilt module providing dependencies
├── model/
│   ├── LoginRequest.kt        # Login request body
│   ├── LoginResponse.kt       # Login response with keypass
│   ├── DashboardResponse.kt   # Dashboard API response
│   └── Entity.kt              # Parcelable entity data class
├── network/
│   └── ApiService.kt          # Retrofit API interface
├── repository/
│   └── AppRepository.kt       # Data repository
├── login/
│   ├── LoginFragment.kt       # Login UI
│   └── LoginViewModel.kt      # Login business logic
├── dashboard/
│   ├── DashboardFragment.kt   # Dashboard UI with RecyclerView
│   ├── DashboardViewModel.kt  # Dashboard business logic
│   └── DashboardAdapter.kt    # RecyclerView Adapter
└── details/
    └── DetailsFragment.kt     # Entity details UI
```