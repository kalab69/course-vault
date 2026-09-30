import { useEffect, useState } from "react";
import logo from "../assets/logo.png";

function SplashScreen({ onFinish }) {
    const [title, setTitle] = useState("");

    const fullText = "CourseVault";

    // Typewriter effect
    useEffect(() => {
        let index = 0;

        const interval = setInterval(() => {
            setTitle(fullText.substring(0, index + 1));
            index++;

            if (index === fullText.length) {
                clearInterval(interval);
            }
        }, 150);

        return () => clearInterval(interval);
    }, []);

    // Finish splash screen after 4 seconds
    useEffect(() => {
        const timer = setTimeout(() => {
            onFinish();
        }, 3000);

        return () => clearTimeout(timer);
    }, [onFinish]);

    return (
        <div className="splash-screen">

            <div className="splash-content">

                <img
                    src={logo}
                    alt="CourseVault Logo"
                    className="splash-logo"
                />

                <h1 className="splash-title">
                    {title}
                </h1>

            </div>

        </div>
    );
}

export default SplashScreen;