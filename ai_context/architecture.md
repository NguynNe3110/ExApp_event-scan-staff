# Architecture

Pattern: MVVM

Flow:
UI -> ViewModel -> Repository -> Data Source

Details:

1. UI:

- Observes StateFlow / LiveData
- Sends user actions to ViewModel

2. ViewModel:

- Holds UI state
- Calls Repository

3. Repository:

- Decides data source (API or Room)

4. Data layer:

- Remote: Retrofit API
- Local: Room Database
