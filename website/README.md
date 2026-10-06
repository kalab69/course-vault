CourseVault — Web Application

CourseVault is a modern, light-themed web platform built with React and Spring Boot. It allows university students to browse courses, access lecture notes, view midterm and final exam papers, open PDF and image resources directly in the browser, and track download statistics from a central dashboard.

⚡ Quick Start & Setup
Prerequisites

Before running CourseVault, make sure you have the following installed:

Node.js v18.0.0 or higher

npm

Java JDK 17 or higher

Maven (the project also includes the Maven Wrapper)

🚀 Running the Application

The backend must be running before starting the React frontend.

Step 1: Start the Backend

Open a terminal in the backend directory:

cd backend


Start the Spring Boot server:

./mvnw spring-boot:run


On Windows, use:

mvnw.cmd spring-boot:run


The backend should start at:

http://localhost:8080


⚠️ Important: Make sure the backend is running before launching the frontend.

Step 2: Install Frontend Dependencies

Open a new terminal window and navigate to the frontend directory:

cd website


Install the required dependencies:

npm install

Step 3: Start the React Frontend

Run the development server:

npm start


Once the application starts, open:

http://localhost:5173


in your browser.

✨ Features
📊 Dashboard & Statistics

The dashboard provides an overview of the student's academic resources, including:

Total enrolled courses

Number of downloaded resources

Enrolled course overview

Quick access to course workspaces

📚 Course Resource Workspace

Each course has a dedicated workspace where students can browse different types of learning materials:

Lecture Notes

Midterm Exams

Final Exams

External Links

AI Course Summaries

📄 Resource Viewer & Downloads

CourseVault supports direct access to learning resources.

Students can:

Open PDF files directly in the browser

View supported images

Download resources to their device

Access external learning links

🤖 AI Course Summaries

The application provides AI-generated course summaries containing:

Key topics

Course breakdowns

Important concepts

Quick revision information

🎨 Responsive Light Theme

CourseVault uses a clean, modern light-themed interface designed for readability and ease of navigation across different screen sizes.

📂 Project Structure
CourseVault/
│
├── backend/
│   └── ...                 # Spring Boot backend
│
├── frontend/
│   ├── public/
│   │
│   ├── src/
│   │   ├── components/
│   │   │   ├── Header.jsx
│   │   │   ├── StatCards.jsx
│   │   │   └── Navigation.jsx
│   │   │
│   │   ├── pages/
│   │   │   ├── Dashboard.jsx
│   │   │   └── CourseDetail.jsx
│   │   │
│   │   ├── App.jsx
│   │   └── index.css
│   │
│   ├── package.json
│   └── README.md
│
└── README.md

Frontend Directory Overview
File / Directory	Description
components/	Shared UI components such as headers, navigation, and statistics cards
pages/Dashboard.jsx	Main dashboard containing statistics and enrolled courses
pages/CourseDetail.jsx	Course resources, filters, AI summaries, and file viewing
App.jsx	Application and route configuration
index.css	Global and component styling
public/	Public/static frontend assets
package.json	Frontend dependencies and scripts
💡 How to Use
1. Open the Dashboard

After launching the application, the dashboard displays your:

Total enrolled courses

Downloaded resource count

Available courses

2. Browse Courses

Select a course card from the Dashboard or Browse page to open the course workspace.

3. Filter Course Materials

Use the resource filter tabs to quickly find the material you need:

Notes | Midterm | Final | Links | AI Summary

4. Open or Download Resources

Select a resource to open it directly in the browser.

For supported files such as PDFs and images, you can either:

View the file directly in the browser

Download the file using the Download ⬇ button

5. View AI Summaries

Select AI Summary from the course resource filters to view generated summaries and key topics for the course.

🔧 Development
Backend

The backend is built using:

Java

Spring Boot

Maven

Default backend address:

http://localhost:8080

Frontend

The frontend is built using:

React

JavaScript / JSX

CSS

npm

Default frontend address:

http://localhost:3000

🔄 Application Architecture

CourseVault consists of two main parts:

┌─────────────────────────┐
│     React Frontend      │
│    localhost:3000       │
└────────────┬────────────┘
             │
             │ HTTP Requests
             ▼
┌─────────────────────────┐
│    Spring Boot Backend  │
│    localhost:8080       │
└─────────────────────────┘


The React frontend communicates with the Spring Boot backend to retrieve courses, resources, statistics, and other application data.

⚠️ Troubleshooting
Backend is not running

If the frontend cannot load course or resource data, first check that the Spring Boot backend is running:

cd backend
./mvnw spring-boot:run

Frontend dependencies are missing

Run:

cd frontend
npm install


Then restart the frontend:

npm start

Port already in use

CourseVault expects:

Frontend: http://localhost:3000
Backend:  http://localhost:8080


If either port is already being used by another application, stop the conflicting process or configure CourseVault to use a different port.

📌 Important Notes

Start the backend before the frontend.

Make sure Java 17+ is installed and available in your system PATH.

Make sure Node.js 18+ and npm are installed.

Keep both the backend and frontend servers running during development.

The frontend expects the backend API to be available on localhost:8080.

🎯 Project Goal

CourseVault is designed to provide university students with a centralized platform for managing and accessing academic resources.

Instead of searching through multiple locations for lecture notes, previous exams, links, and summaries, students can access their course materials from one organized workspace.

📄 License

This project is intended for educational and academic purposes.
