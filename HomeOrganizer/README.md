# Inventory Management App

This project is an inventory management app developed in Swift for iOS. The app allows users to manage profiles, categories, and items, and keep track of what needs to be bought. It supports synchronization between devices and notifications using Firebase.

## Features

- Manage profiles, categories, and items.
- Mark items as "needs to buy" and track them in a shopping list.
- Open item links directly from the app.
- Real-time synchronization between devices using Firebase Firestore.
- Notifications for updates using Firebase Cloud Messaging.

## Prerequisites

- Xcode 11 or later
- CocoaPods
- Firebase account

## Setup

### 1. Clone the Repository

sh
git clone https://github.com/your-username/inventory-management-app.git
cd inventory-management-app

2. Install CocoaPods

Ensure you have CocoaPods installed. If not, you can install it using:

sh

sudo gem install cocoapods

3. Install Dependencies

Create a Podfile if it doesn't exist and add the following dependencies:

ruby

platform :ios, '11.0'

target 'Inventory' do
  use_frameworks!

  pod 'Firebase/Firestore'
  pod 'Firebase/Messaging'
end

Install the dependencies:

sh

pod install

4. Open the Xcode Workspace

Open the .xcworkspace file in Xcode:

sh

open Inventory.xcworkspace

5. Configure Firebase

    Go to the Firebase Console.
    Create a new project.
    Add an iOS app to your project and register it with your app’s bundle ID.
    Download the GoogleService-Info.plist file.
    Add the GoogleService-Info.plist file to your Xcode project.

6. Configure AppDelegate

Update your AppDelegate to configure Firebase and request notification permissions:

swift

import UIKit
import Firebase
import UserNotifications

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        FirebaseApp.configure()

        // Request permission for notifications
        let center = UNUserNotificationCenter.current()
        center.delegate = self
        center.requestAuthorization(options: [.alert, .sound, .badge]) { granted, error in
            if let error = error {
                print("Error requesting notification authorization: \(error)")
            }
        }

        application.registerForRemoteNotifications()

        return true
    }

    // Handle notification registration
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        let tokenParts = deviceToken.map { data in String(format: "%02.2hhx", data) }
        let token = tokenParts.joined()
        print("Device Token: \(token)")
    }

    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        completionHandler([.alert, .sound, .badge])
    }
}

7. Implement FirestoreManager

Create a FirestoreManager to handle Firestore operations:

swift

import FirebaseFirestore
import FirebaseFirestoreSwift

class FirestoreManager {
    let db = Firestore.firestore()

    func saveProfile(profile: Profile) {
        do {
            try db.collection("profiles").document(profile.id.uuidString).setData(from: profile)
        } catch let error {
            print("Error writing profile to Firestore: \(error)")
        }
    }

    func fetchProfiles(completion: @escaping ([Profile]) -> Void) {
        db.collection("profiles").getDocuments { (snapshot, error) in
            if let error = error {
                print("Error getting profiles: \(error)")
                return
            }
            let profiles = snapshot?.documents.compactMap { document in
                try? document.data(as: Profile.self)
            } ?? []
            completion(profiles)
        }
    }

    // Add more methods as needed for categories and items
}

8. Implement AppLogic

Update your AppLogic to use Firestore for data management:

swift

class AppLogic: ObservableObject {
    @Published var profiles: [Profile] = []
    private let firestoreManager = FirestoreManager()

    init() {
        loadProfiles()
    }

    func addProfile(_ profile: Profile) {
        profiles.append(profile)
        firestoreManager.saveProfile(profile: profile)
    }

    func loadProfiles() {
        firestoreManager.fetchProfiles { [weak self] profiles in
            DispatchQueue.main.async {
                self?.profiles = profiles
            }
        }
    }

    // Add more methods for categories and items
}

Usage

    Build and run the app on your device or simulator.
    Add profiles, categories, and items.
    Mark items as "needs to buy" and view them in the shopping list.
    Long-press on item names in the shopping list to open their links.

Future Enhancements

    Implement more robust error handling.
    Add more features for item management and notifications.
    Improve the user interface for a better user experience.

License

This project is licensed under the MIT License.
Contact

If you have any questions or feedback, please feel free to contact gondicka@gmail.com
