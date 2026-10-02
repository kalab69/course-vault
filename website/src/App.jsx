import { useState } from "react";
import { Routes, Route } from "react-router-dom";

import SplashScreen from "./components/SplashScreen";

import Home from "./pages/Home";
import Courses from "./pages/Courses";
import CourseDetails from "./pages/CourseDetail";
import Footer from "./pages/Footer";
import NavBar from "./components/NavBar";
import MyCourses from './pages/MyCourses';

function App() {

    const [showSplash, setShowSplash] = useState(true);

    if (showSplash) {
        return (
            <SplashScreen
                onFinish={() => setShowSplash(false)}
            />
        );
    }

    return (
        <>
        <NavBar/>
        <Routes>

            <Route path="/" element={<Home />} />

            <Route path="/courses" element={<Courses />} />
            <Route path="/MyCourses" element={<MyCourses />} />

            <Route path="/courses/:id" element={<CourseDetails />} />
            
        </Routes>
        <Footer/>
    </>);
}

export default App;