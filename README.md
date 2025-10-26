# Movies & TV Shows App

## Project Overview
This is an **Android app built with Jetpack Compose** that fetches **Movies and TV Shows** from the **Watchmode API**.  
The app demonstrates **MVVM architecture**, **Retrofit networking**, **Hilt dependency injection**, and basic navigation in Compose.

---

## Features Implemented

### Home Screen
- Toggle tab to switch between **Movies** and **TV Shows**.
- Displays a list of items with **poster and title**.
- Shows a **loading indicator** while fetching data.

### Details Screen
- Displays **poster, title, year, and type** of the selected item.
- **TopAppBar** with back navigation.

### Error Handling
- Displays **Toast messages** for API failures.

### Architecture
- **MVVM pattern**: Repository → ViewModel → UI.
- **Hilt** for dependency injection.

### Networking
- **Retrofit** used for API calls.

---

## Features Not Fully Implemented
- **Toast and Snack Bar** for some cases (partially implemented).  
- **Shimmer effect** replaced with a simple `CircularProgressIndicator` due to time constraints.  
- **Full details** (description, release date) not fetched dynamically — currently passed from HomeScreen.  
- **RxKotlin Single.zip integration** for simultaneous API calls was not implemented; coroutines are used instead.

> These limitations are due to **time constraints**.


Demo video link: https://drive.google.com/file/d/1qZtypw1zWm56-xUEtm_thi5383Zuo5ET/view?usp=drive_link
## Unit Test Cases

| Test Case ID | Feature Tested       | Input                           | Expected Output                                | Actual Output   | Status (Pass/Fail) |
|--------------|--------------------|---------------------------------|-----------------------------------------------|----------------|------------------|
| TC01         | Fetch Movies API    | Launch app → “Movies” tab selected | Movie list with posters and titles should load | As expected    | ✅ Pass           |
| TC02         | Fetch TV Shows API  | Toggle to “TV Shows” tab        | TV show list should display properly           | As expected    | ✅ Pass           |
| TC03         | Navigate to Details Page | Tap on any movie item       | Details screen should show title, description, poster | As expected | ✅ Pass           |
| TC04         | Back Navigation     | Press Back on Details screen    | Should return to Home screen                   | Works fine     | ✅ Pass           |
