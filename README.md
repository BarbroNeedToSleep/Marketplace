# 🛒 Marketplace API – Mini Project

## 🎯 Project Goal

Build a **Spring Boot REST API** that allows users to **create and publish advertisements** using their **email and password**.

- If the email exists → validate the password.
- If the email doesn't exist → register the user.
- Then allow the ad to be created and saved.

---

## 🚀 Core Features

### 1. ✅ Create Advertisement with Login or Auto-Registration

- **Endpoint:** `POST /api/ads`
- **Request must include:**
  - Email
  - Password
  - Ad details (title, description, expiration date, etc.)
- **Logic:**
  - If the email is **not registered**, create a new user and save the ad.
  - If the email **is already registered**, verify the password:
    - If correct → save the ad.
    - If incorrect → return `401 Unauthorized`.

### 2. 📋 Get All Active Ads

- **Endpoint:** `GET /api/ads`
- Return only **non-expired ads**.
- Support optional filtering (e.g., by category, keyword).

### 3. 👤 Get User’s Ads

- **Endpoint:** `POST /api/ads/my-ads`
- Accepts user credentials (email + password).
- Returns **only the ads created by that user**.

---

## ✅ Required Features

- Auto-registration with email and password
- Validation using `@Email`, `@NotBlank`, `@Future`, etc.
- Use of DTOs (do not expose entities in controllers)
- Password check on ad creation
- Filter out expired ads automatically
- Clean exception handling with `@ControllerAdvice`
- API documentation with Swagger

---

## 💡 Optional Task (Advanced)

🔐 1. Auto-Generate Password & Email It to User

> Instead of requiring users to enter a password manually, allow the app to **automatically generate a password** and **send it to the user's email**.

📧 2. Send Email Notification When an Ad is Created

> Send an **email confirmation** to the user after successfully creating a new advertisement.

---

## 🧱 Suggested Architecture

```
com.example.marketplace
│
├── controller       // REST endpoints
├── service          // Business logic
├── repository       // DB access
├── model/entity     // User, Advertisement
├── dto              // Request/response models
├── exception        // Custom exceptions
└── config           // Email config, validation, security
```

---

## 🧪 Tools & Tech

- Java 17+
- Spring Boot (Web, JPA, Validation)
- MySQL (main DB), H2 (tests)
- Maven or Gradle

---

## 📘 Instructions

### 🛠 After Creating Your Spring Boot Project:

- Push your project as a **private GitHub repo**
- Add your **teacher as a collaborator**

### 🔁 Git & Development Workflow:

- Use clear commit messages like:
  - `add: advertisement creation API`
  - `test: AdService unit test`
- Start with **Core Features** in a separate branch
- After completing them, create another branch for **Optional Tasks**
- Keep code clean and organized by responsibility
