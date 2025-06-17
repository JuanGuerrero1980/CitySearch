# 🌍 City Explorer App

An Android app to browse and search cities, view detailed information, mark favorites, and display current weather and location on the map. Supports modern Android architecture and responsive layouts for portrait and landscape.

---

## ✨ Features

- 🔎 Search cities by name (with live updates)
- 🗺️ View city location on Google Map
- 🌦️ Show real-time weather info (OpenWeatherMap API)
- ⭐ Mark/unmark favorite cities
- 🧭 Master-detail layout on landscape orientation
- 🧵 Jetpack Compose UI
- 🧠 Clean architecture with ViewModel, Repository & UseCases
- 🔄 Paging 3 for efficient list loading
- 📦 Room for local data storage
- 🧪 Unit and integration tests

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM + Clean Architecture
- **Networking:** Retrofit
- **Persistence:** Room
- **Pagination:** Paging 3
- **Dependency Injection:** Hilt
- **Preferences Storage:** Jetpack DataStore
- **Map:** Google Maps SDK
- **Weather API:** OpenWeatherMap

---

## 🚀 Getting Started

### Requirements

- Android Studio Hedgehog or newer
- Kotlin 1.9+
- OpenWeatherMap API key
- Google Maps API key

### Setup

1. Clone the repository:

```bash
git clone https://github.com/yourusername/city-explorer-app.git
```

2. Add your API keys in local.properties:
weather_api_key=YOUR_OPENWEATHERMAP_KEY
google_maps_key=YOUR_GOOGLE_MAPS_KEY

3. Sync the project and run on emulator or device.

## 🧪 Testing

The project includes both **unit tests** and **integration tests**:

### ✅ Unit Tests

- `CityUseCaseTest`: tests logic to determine whether cities should be downloaded.
- `CityViewModelTest`: tests state updates, search handling, and favorite toggling.
- Uses **JUnit4**, **Turbine**, and **Mockito/Kotlinx Coroutines Test**.

### 🧩 Integration Tests

- `CityDaoTest`: tests Room queries with an in-memory database.
- `CityRepositoryIntegrationTest`: uses real DAO and database to test interactions.
- Covers: insertion, search filters, pagination, and favorite status.