import { useState } from "react";
import SplashScreen from "./components/SplashScreen";

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
        <div>
            {/* Your actual website */}

            <h1>CourseVault Website</h1>

        </div>
    );
}

export default App;