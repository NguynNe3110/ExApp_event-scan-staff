# Example Flow: Login

1. User clicks login button
2. Activity calls ViewModel.login()
3. ViewModel calls AuthRepository.login()
4. Repository calls API
5. Result returned to ViewModel
6. ViewModel updates UI state
7. UI observes state and updates screen
