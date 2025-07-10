
# 🎓 CGPA Calculator App

An Android application to help university students calculate and manage their CGPA. Built with **Java** and **Firebase Firestore**, the app supports real-time data storage, dynamic GPA input, and profile management — all within a clean Material Design interface.

---

## ✨ Features

- 🧑‍🎓 **Welcome Dialog** – Collects student’s name, roll number, department, and semester.
- ➕ **Dynamic Course Entry** – Add multiple courses with credit hours and grades using a flexible TableLayout.
- 🧮 **CGPA Calculation** – Calculates CGPA based on user input and previously earned GPA/CH.
- ☁️ **Cloud Integration** – Uses Firebase Firestore to store and retrieve GPA records.
- 📋 **User Info Banner** – Displays the logged-in student’s profile at the top of the screen.
- 📄 **View GPA Records** – Displays the **latest** GPA record per user using a clean RecyclerView.
- 💾 **Local Storage** – Remembers user profile via SharedPreferences.
- 🧹 **Clear Fields** – Reset all input fields to start fresh.

---

## 🔧 Tech Stack

| Tech          | Usage                         |
|---------------|-------------------------------|
| Java          | Core Android app logic        |
| Firebase      | Cloud database (Firestore)    |
| XML           | UI layouts (Material Design)  |
| RecyclerView  | Display user GPA records      |
| SharedPreferences | Local data persistence   |
| Gradle        | Build automation              |
| Android Studio | Development environment     |

---

## 📂 Project Structure

```

📁 app/
├── 📁 java/com/uetoffical/cgpacalculator/
│   ├── MainActivity.java           # CGPA calculator logic
│   ├── AllUsersActivity.java       # RecyclerView of all user GPA records
│   ├── UserGpaRecord.java          # Model class
│   ├── UserGpaAdapter.java         # Adapter for RecyclerView
│
├── 📁 res/layout/
│   ├── activity\_main.xml           # Main form layout
│   ├── activity\_all\_users.xml      # GPA list screen
│   ├── dialog\_welcome.xml          # User info dialog
│   ├── item\_user\_gpa.xml           # Each GPA item layout
│   └── course\_row\.xml              # Dynamic course entry row
│
├── 📁 res/values/
│   └── strings.xml                 # Grade list and static text

````

---

## 🔐 Firebase Setup Instructions

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a project and register your Android app
3. Download the `google-services.json` file and place it in the `/app` directory
4. Enable **Firestore Database**
5. Use the following **test rules** during development:

```js
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true; // 👈 For testing only!
    }
  }
}
````

---

## 🚀 Getting Started

```bash
git clone https://github.com/yourusername/CGPA-Calculator.git
cd CGPA-Calculator
```

1. Open the project in Android Studio.
2. Place your `google-services.json` in `app/`.
3. Click **Run** on emulator or physical device.

---

## 📸 Screenshots

| Main Form |
|-----------|
| <img src="https://github.com/user-attachments/assets/d15416a0-373e-4303-acfc-2fe49dcce4b0" alt="Main Screen" width="300"/> |

 



## 📈 Future Improvements

* 🔐 User Authentication (login)
* 📤 Export GPA record as PDF
* 📊 Visual CGPA trends over semesters
* 📁 Offline support

---

## 🙌 Acknowledgements

Developed by **Khalil Akbars**
Department of **Institute of Business & Management(IB&M)**, **University of Engineering & Technology,Lahore**

---

## 📄 License

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

```

---

✅ **Instructions:**
- Save this as `README.md` in the root of your project.
- Replace screenshot URLs with your actual GitHub image links.
- Replace `yourusername` with your GitHub username.

Would you like help generating a `CONTRIBUTING.md` file or a GitHub issue template?
```
