import heroImage from "../assets/hero.png";
function Hero(){
    return(
           <section className="hero-section">

                <div className="hero-content">

                    <div className="hero-badge">
                        🎓 Built for university students
                    </div>

                    <h1>
                        Your university resources.
                        <span> All in one place.</span>
                    </h1>

                    <p>
                        CourseVault is a shared study resource platform for
                        university students. Find organized notes, past exams,
                        and curated tutorials for your courses — without
                        digging through scattered Telegram groups and
                        shared drives.
                    </p>

                    <div className="hero-actions">
                        <button className="primary-button">
                            Explore Courses
                        </button>

                        <button className="secondary-button">
                            How it works
                            <span>→</span>
                        </button>
                    </div>

                    <div className="hero-stats">
                        <div>
                            <strong>Organized</strong>
                            <span>Course resources</span>
                        </div>

                        <div>
                            <strong>Accessible</strong>
                            <span>Anytime, anywhere</span>
                        </div>

                        <div>
                            <strong>Student-focused</strong>
                            <span>Built for learning</span>
                        </div>
                    </div>

                </div>

                <div className="hero-visual">
                    <div className="hero-glow"></div>

                    <img
                        src={heroImage}
                        alt="CourseVault study resources"
                    />

                </div>

            </section>
    )
}
export default Hero