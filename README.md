# <img src="frontend/course-vault-frontend/src/main/Resources/Images/Logo%20(1).png" width="40" style="vertical-align: middle; margin-right: 10px;" alt="Course Icon"><span style="vertical-align: middle;">Course Vault</span>

An intuitive, robust and professional platform designed to streamline course management, content delivery and academic administration. **Course Vault** provides a seamless experience for students exploring educational content through a public website and desktop app, while offering a comprehensive, secure dashboard for administrators to oversee operations.

---

## 🚀 Live Demo

Experience the platform live: **[Visit Course Vault Website](https://course-vault-website.vercel.app/)**

---

## 📱 Project Architecture & Structure

CourseVault is a shared study resource platform for university students. Find organized notes, past exams, and curated tutorials for your courses without digging through scattered Telegram groups and shared drives.

### Backend (`backend/`)
The engine of Course Vault is powered by **Spring Boot**, leveraging Java's robust ecosystem to provide a highly secure and scalable RESTful API.
*   **Architecture:** Built following clean architectural principles with controllers, services and repositories.
*   **Data Management:** Utilizes Spring Data JPA for efficient, relational database management and transactional integrity.

### Frontend (`frontend/`)
The consumer-facing ecosystem is split into two specialized web and desktop applications to provide tailored user experiences.

#### 1. 🌐 The Website (`website/`)
The primary gateway for students and public visitors.
*   **Features:** Offers a modern, clean and highly responsive user interface where users can browse cataloged courses and explore educational resources.
*   **Performance:** Optimized for fast loading times and seamless client-side navigation.

#### 2. 🔐 The Admin Page (`admin-page/`)
The command center for platform administrators.
*   **Features:** A restricted-access dashboard built to handle comprehensive CRUD (Create, Read, Update, Delete) operations. Admins can seamlessly add new courses, update lessons, manage user enrollments and track system metrics.

---

## Screenshots

### Public Website
*Place your website screenshots here to showcase the user experience.*
![Website Dashboard]()

### Admin Dashboard
*Place your admin dashboard screenshots here to showcase management features.*
![Admin Panel](https://course-vault-website.vercel.app/Courses)

---

## Step-by-Step Guide: Running the Admin Page locally

Follow these clear, sequential instructions to launch the Admin Page on your local machine using the Windows Command Prompt (cmd).

### Prerequisite Check
Before you begin, make sure your machine has **Node.js** installed. You can verify this by opening a command prompt and running:
```cmd
node -v
```

### Setup Instructions

1. **Open Command Prompt**
   Press the `Windows Key`, type `cmd`, and hit `Enter` to open a new command line window.

2. **Navigate to the Root Project Directory**
   Use the `cd` command to change directories to where you cloned the repository:
   ```cmd
   cd path\to\your\cloned\course-vault
   ```

3. **Navigate into the Admin Page Folder**
   Change your directory explicitly into the admin interface package:
   ```cmd
   cd admin-page
   ```

4. **Install System Dependencies**
   Run the package manager setup command to install all necessary UI nodes and development libraries:
   ```cmd
   npm install
   ```
   > *Note: This might take a minute or two depending on your internet connection speed.*

5. **Launch the Local Development Server**
   Execute the local build and run scripts by typing:
   ```cmd
   npm start
   ```
   *(Alternatively, if your frontend configuration specifies it, use `npm run dev`)*

6. **Access the Application**
   Once the console compiles successfully, open your web browser and navigate to the address displayed in your terminal window (typically `http://localhost:3000` or `http://localhost:5173`).
