Money App

Overview

This is a Money App that allows users to manage accounts, transfer money, and view transaction history while ensuring secure authentication via Firebase. The app follows the MVVM architecture and uses a bottom navigation bar for smooth navigation.
Features

User Authentication: Firebase Authentication (Email/Password) with email verification.

Account Management: Users can view their accounts in a RecyclerView.

Money Transfer: Form validation ensures transfers do not exceed account balance.

Transaction History: Transactions are stored locally using Room and displayed in a RecyclerView.

Navigation: Bottom navigation bar for easy access to major screens.

Auto Logout: Firebase session expiration automatically logs users out.

Unit Tests: MockK used for testing ViewModels.

Technologies Used

Programming Language: Kotlin

Architecture: MVVM

Libraries:

Firebase Authentication for user login

RecyclerView for displaying lists

Room for local database management

LiveData for data observation

ViewModel for managing UI-related data

Coroutines for background tasks

MockK for unit testing

Installation & Setup

Clone the Repository

git clone https://github.com/collinsceleb/money-app.git

cd money-app


Open in Android Studio


Open Android Studio.

Select "Open an existing project" and navigate to the cloned repository.

Set Up Firebase


Go to Firebase Console.

Create a new project and register your Android app.

Download the google-services.json file and place it inside app/.

Enable Email/Password authentication in Firebase.

Build the Project


In Android Studio, click Build → Make Project.

Ensure Gradle syncs successfully.

Run the App


Connect a physical device or start an emulator.

Click Run (Shift + F10) or use the play button.

Running Tests

To run unit tests:

./gradlew test

Or, from Android Studio:

Open AccountViewModelTest.kt or TransactionViewModelTest.kt

Right-click and select Run Tests

Usage

Register: Users must sign up with an email and password.

Verify Email: After registration, users must verify their email before logging in.

Login: Redirects to the main activity after successful authentication.

Transfer Money: Users can transfer money between accounts with validation checks.

View Transactions: Users can view past transactions stored in Room.

Logout: Users can manually log out, or they will be logged out automatically after session expiration.

Notes

The app does not support real transactions—it's a simulation.

Auto logout is managed by Firebase session expiration.

Bottom navigation provides seamless access to major features.

Fragments are avoided for simplicity.

Future Improvements

Improve UI with animations.

Implement Dark Mode support.

Add push notifications for transactions.

Developed by Kolawole Afuye


