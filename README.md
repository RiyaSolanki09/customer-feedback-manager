# 🔒 Secure Customer Feedback Manager

## Project Overview

This is a personal full-stack web application developed using **Spring Boot** and **Thymeleaf**. The project is designed to provide a secure and efficient system for collecting, encrypting, and reviewing customer feedback.

### Key Features

* **AES-256 Encryption:** Integrates Java's `Cipher` and `SecretKeySpec` to encrypt sensitive feedback content **before** persistence to the H2 database, ensuring data confidentiality.
* **Spring Security:** Implements form-based authentication to restrict access to the Admin Review dashboard using a dedicated `ADMIN` role.
* **Admin View & Filtering:** A secure dashboard allowing administrators to view all **decrypted** feedback entries and filter results by Customer Name, Rating, and Submission Date.
* **Modern UI/UX:** Uses **Bootstrap 5** for a responsive, professional, and visually appealing user interface.
* **Data Persistence:** Configured to use an H2 file-based database for local development, allowing data to persist between application restarts.

## 🛠️ Technology Stack

* **Backend:** Java 8, Spring Boot 2.7 (latest version compatible with Java 8)
* **Database:** H2 (in-memory for quick testing, file-based for persistence)
* **Web Layer:** Spring MVC, Thymeleaf
* **Security:** Spring Security
* **Build Tool:** Maven

## 🚀 Getting Started

### Prerequisites

* Java Development Kit (JDK) 8
* Apache Maven 3+
* Git

### Setup and Running Locally

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/RiyaSolanki09/customer-feedback-manager.git](https://github.com/RiyaSolanki09/customer-feedback-manager.git)
    cd customer-feedback-manager
    ```

2.  **Verify Configuration:** The application uses an in-memory H2 database for persistence (files stored in the local `/data` folder) and a dedicated encryption key. These settings are located in `src/main/resources/application.properties`.

3.  **Build the Project:**
    ```bash
    mvn clean install
    ```

4.  **Run the Application:**
    ```bash
    mvn spring-boot:run
    ```

The application will start on the custom port: `http://localhost:8880`

## 🔑 Application Access

### 1. User Submission Form (Public)

* **URL:** `http://localhost:8880/`
* **Access:** Open to all users to submit encrypted feedback.

### 2. Admin Review Dashboard (Secure)

* **URL:** `http://localhost:8880/admin/feedback`
* **Access:** Requires authentication.

| Role | Username | Password |
| :--- | :--- | :--- |
| **Admin** | `admin` | `password123` |

### Database Console (H2)

For direct schema inspection (requires Admin login via browser first):

* **URL:** `http://localhost:8880/h2-console`
* **JDBC URL:** `jdbc:h2:mem:feedbackdb` (or check your `application.properties` for the file-based path if configured for persistence)
* **User:** `sa`
* **Password:** (Leave blank)

## 📄 License

This project is open-source and available under the MIT License.

---

