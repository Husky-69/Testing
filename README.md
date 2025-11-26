# M-Kulima Android App

A native Android application designed to empower smallholder farmers with data-driven agricultural insights, offline-first capabilities, and actionable farming guidance.

## Overview

M-Kulima bridges the technology gap for smallholder farmers in tech-underserved regions by providing:

- **Offline-First Architecture**: Core features work without active internet
- **Hyper-Local Intelligence**: Weather, soil, and crop advice specific to the user's location
- **Action-Oriented Insights**: Data translated into simple, actionable tasks
- **Multi-Modal Interaction**: Visual design for low-literacy accessibility

## Key Features

### 1. Onboarding Screen
- Farmer profile setup
- Location and crop selection
- Farm size input

### 2. Home Dashboard
- **Weather Alerts**: Real-time temperature and conditions
- **Pest Alerts**: Critical pest notifications
- **Daily Tasks**: Action-oriented farming tasks
- **Navigation Hub**: Quick access to diagnosis, guides, and farm info

### 3. Pest & Disease Diagnosis
- Camera integration for plant disease detection
- Photo upload from gallery
- AI-powered diagnosis with recommendations

### 4. Crop Guides
- Curated agricultural best practices
- County-specific guidance
- Seasonal crop recommendations

### 5. My Farm
- Profile management
- Farm information tracking
- Activity history

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Navigation**: Compose Navigation
- **Backend**: Supabase (PostgreSQL + Real-time APIs)
- **Authentication**: Supabase Auth
- **Architecture**: MVVM with Repository pattern

## Project Structure

```
com/mkulima/app/
├── MainActivity.kt
├── data/
│   ├── models/
│   │   └── FarmerModels.kt
│   ├── SupabaseClient.kt
│   └── FarmerRepository.kt
├── navigation/
│   └── Navigation.kt
├── ui/
│   ├── screens/
│   │   ├── OnboardingScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── DiagnosisScreen.kt
│   │   ├── CropGuidesScreen.kt
│   │   └── MyFarmScreen.kt
│   ├── theme/
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodel/
│       └── FarmerViewModel.kt
└── res/
    ├── values/
    │   ├── strings.xml
    │   └── colors.xml
```

## Data Models

### Farmer
- Profile information (name, location, county)
- Farm details (main crop, size in hectares)
- Contact information

### Weather Alert
- County-specific weather data
- Temperature and conditions
- Risk level assessment

### Pest Alert
- Pest/disease identification
- Severity level
- Recommended treatment

### Daily Task
- Action-oriented farming tasks
- Due dates
- Completion status

### Crop Price
- Market pricing information
- Location-specific pricing
- Real-time updates

### Diagnosis Result
- AI diagnosis from plant photos
- Confidence scores
- Treatment recommendations

## Database Schema

All tables implement Row Level Security (RLS) with these security policies:

- **Farmers**: Users can only access their own profile
- **Tasks**: Users can only access their own tasks
- **Diagnosis**: Users can only access their own diagnosis history
- **Alerts**: Public read access for all users
- **Prices**: Public read access for all users

## Setup Instructions

### Prerequisites

- Android Studio (Arctic Fox or later)
- Kotlin 1.9.20+
- Target SDK 34

### Environment Configuration

1. Replace `YOUR_SUPABASE_URL` and `YOUR_SUPABASE_ANON_KEY` in `SupabaseClient.kt` with your Supabase credentials
2. Ensure Supabase project has the M-Kulima schema migration applied

### Build & Run

```bash
./gradlew build
./gradlew installDebug
```

## Design System

### Color Palette
- **Primary**: #2E7D32 (Green) - Growth and agriculture
- **Secondary**: #558B2F - Accent green
- **Tertiary**: #8BC34A - Light green
- **Error**: #B3261E - Alerts

### Typography
- **Headlines**: Bold, 24-32sp
- **Body**: Regular, 14-16sp
- **Labels**: Medium weight, 12sp

## Responsive Design

The app is optimized for:
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)
- Screen sizes: Phone form factors (4.5" - 6.7")

## Security Considerations

- All authentication via Supabase Auth (email/password)
- Row Level Security enforced on all tables
- No cleartext traffic allowed
- Runtime permissions for camera and location
- Secure credential storage via Supabase

## Offline Capabilities

Core functionality available without internet:
- View farmer profile
- Access stored crop guides
- Review previous diagnosis results
- View cached weather data
- Complete daily tasks

Data syncs when connection is restored.

## Future Enhancements

- TensorFlow Lite model for on-device pest detection
- SMS integration for farmers with limited data
- Voice-based input for low-literacy users
- Machinery rental marketplace
- Expert chat with agricultural extension officers
- Credit scoring based on farming activity

## Performance Optimization

- Lightweight Compose UI (no heavy fragments)
- Efficient database queries with proper indexing
- Lazy loading for lists and images
- Minimal network requests with caching

## Support & Contact

For issues or feature requests, please contact the M-Kulima development team or file an issue in the project repository.

## License

M-Kulima is developed for smallholder farmer empowerment. Terms available upon request.

---

**Version**: 1.0.0
**Last Updated**: November 2025
