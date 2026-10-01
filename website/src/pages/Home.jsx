import heroImage from "../assets/hero.png";

function Home() {
    return (
        <main className="home">

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
            <section className="what-is-coursevault">

                <div className="section-label">
                    What is CourseVault?
                </div>

                <h2>
                    Everything you need to study.
                    <span> All in one place.</span>
                </h2>

                <p className="section-description">
                    CourseVault brings together useful study resources,
                    external learning links, and AI-powered course summaries
                    so you can spend less time searching and more time learning.
                </p>


                <div className="feature-grid">

                    {/* TELEGRAM */}
                    <div className="feature-card">

                        <h3>
                            No more scattered
                            <br />
                            resources from Telegram
                        </h3>

                        <div className="telegram-illustration">

                            <div className="phone">

                                <div className="phone-header">
                                    <img
                                        src="https://cdn.simpleicons.org/telegram"
                                        alt="Telegram"
                                    />
                                    <span>Study Group</span>
                                </div>

                                <div className="message">
                                    Can anyone share
                                    <br />
                                    DSA notes?
                                </div>

                                <div className="message">
                                    Exam papers??
                                </div>

                                <div className="message">
                                    Check this drive
                                </div>

                                <div className="message">
                                    Last year's exam?
                                </div>

                            </div>

                            <div className="floating-file file-one">
                                📄 DSA Notes.pdf
                            </div>

                            <div className="floating-file file-two">
                                📄 Database Exam.pdf
                            </div>

                            <div className="telegram-x">
                                ×
                            </div>

                        </div>

                        <p>
                            Stop wasting time searching through endless
                            messages, old files, and broken links.
                        </p>

                        <strong>
                            Get organized. Save time. Study better.
                        </strong>

                    </div>


                    {/* EXTERNAL LINKS */}
                    <div className="feature-card">

                        <h3>
                            External links for
                            <br />
                            better learning
                        </h3>

                        <div className="links-illustration">

                            <div className="browser-window">

                                <div className="browser-bar">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                </div>

                                <div className="fake-url">
                                    Search learning resources...
                                </div>

                                <div className="resource-link">

                                    <img
                                        src="https://cdn.simpleicons.org/youtube"
                                        alt="YouTube"
                                    />

                                    <div>
                                        <strong>YouTube</strong>
                                        <small>
                                            Data Structures Full Course
                                        </small>
                                    </div>

                                    <span>↗</span>

                                </div>

                                <div className="resource-link">

                                    <div className="resource-logo w3">
                                        W3
                                    </div>

                                    <div>
                                        <strong>W3Schools</strong>
                                        <small>
                                            C++ Programming Tutorial
                                        </small>
                                    </div>

                                    <span>↗</span>

                                </div>

                                <div className="resource-link">

                                    <img
                                        src="https://cdn.simpleicons.org/geeksforgeeks"
                                        alt="GeeksforGeeks"
                                    />

                                    <div>
                                        <strong>GeeksforGeeks</strong>
                                        <small>
                                            Algorithms & Data Structures
                                        </small>
                                    </div>

                                    <span>↗</span>

                                </div>

                            </div>

                        </div>

                        <p>
                            Find useful external resources from YouTube,
                            W3Schools, GeeksforGeeks and more — all organized
                            around your courses.
                        </p>

                        <strong>
                            Curated. Useful. Easy to access.
                        </strong>

                    </div>


                    {/* AI SUMMARY */}
                    <div className="feature-card">

                        <h3>
                            AI summary for
                            <br />
                            every course
                        </h3>

                        <div className="ai-illustration">

                            <div className="ai-card">

                                <div className="ai-title">
                                    <div className="ai-icon">
                                        ✦
                                    </div>

                                    <strong>
                                        AI Summary
                                    </strong>
                                </div>

                                <p>
                                    This course covers the fundamental
                                    concepts of Data Structures including
                                    arrays, linked lists, stacks, queues,
                                    trees, and graphs.
                                </p>

                                <div className="summary-lines">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                </div>

                            </div>

                        </div>

                        <p>
                            Get an AI-generated overview of a course to
                            quickly understand what it covers before
                            diving into the resources.
                        </p>

                        <strong>
                            Understand faster. Study smarter.
                        </strong>

                    </div>


                    {/* NO SIGN UP */}
                    <div className="feature-card">

                        <h3>
                            No sign up.
                            <br />
                            Just access.
                        </h3>

                        <div className="no-signup-illustration">

                            <div className="user-circle">

                                <div className="user-head"></div>

                                <div className="user-body"></div>

                                <div className="slash"></div>

                            </div>

                            <div className="no-signup-badge">
                                ✓ No Sign Up Needed
                            </div>

                        </div>

                        <p>
                            No account. No hassle.
                            Just open CourseVault, explore the courses,
                            and get the resources you need.
                        </p>

                        <strong>
                            Simple. Fast. For students.
                        </strong>

                    </div>

                </div>


                {/* COMPUTER SCIENCE NOTICE */}
                <div className="cs-notice">

                    <div className="cs-icon">
                        &lt;/&gt;
                    </div>

                    <div>
                        <strong>
                            Currently focused on Computer Science students.
                        </strong>

                        <p>
                            More courses and study areas coming soon.
                        </p>
                    </div>

                </div>

            </section>
            <section className="how-it-works" id="how-to-use">

                <div className="how-label">
                    How it works
                </div>

                <h2>
                    Find it. Open it. <span>Study it.</span>
                </h2>

                <p className="how-description">
                    Find your course in seconds, explore organized resources,
                    and study without digging through scattered files and links.
                </p>


                <div className="steps-container">

                    {/* STEP 1 */}
                    <div className="step">

                        <div className="step-number">
                            01
                        </div>

                        <div className="step-content">

                            <h3>
                                Find your course
                            </h3>

                            <p>
                                Search for a course by name or browse courses
                                organized by your year level.
                            </p>


                            <div className="course-browser">

                                <div className="search-box">
                                    <span>⌕</span>
                                    <span className="search-placeholder">
                                        Search course...
                                    </span>
                                </div>


                                <div className="year-title">
                                    Browse by year
                                </div>

                                <div className="year-buttons">

                                    <div className="year active">
                                        First Year
                                    </div>

                                    <div className="year">
                                        Second Year
                                    </div>

                                    <div className="year">
                                        Third Year
                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>


                    {/* STEP 2 */}
                    <div className="step">

                        <div className="step-number">
                            02
                        </div>

                        <div className="step-content">

                            <h3>
                                Open a course
                            </h3>

                            <p>
                                Select a course to see everything available
                                for that course in one organized place.
                            </p>


                            <div className="course-preview">

                                <div className="course-icon">
                                    &lt;/&gt;
                                </div>

                                <div className="course-info">
                                    <strong>
                                        Data Structures
                                    </strong>

                                    <span>
                                        CS201
                                    </span>
                                </div>

                                <span className="open-arrow">
                                    →
                                </span>

                            </div>

                        </div>

                    </div>


                    {/* STEP 3 */}
                    <div className="step">

                        <div className="step-number">
                            03
                        </div>

                        <div className="step-content">

                            <h3>
                                Choose what you need
                            </h3>

                            <p>
                                Navigate between notes, mid exams, final exams,
                                all resources, and the AI course summary.
                            </p>


                            <div className="course-navigation">

                                <div className="course-nav active">
                                    All
                                </div>

                                <div className="course-nav">
                                    Notes
                                </div>

                                <div className="course-nav">
                                    Mid
                                </div>

                                <div className="course-nav">
                                    Final
                                </div>

                                <div className="course-nav ai">
                                    ✦ AI Summary
                                </div>

                            </div>


                            <div className="resource-mini-list">

                                <div>
                                    <span>📄</span>
                                    Lecture Notes
                                    <b>↓</b>
                                </div>

                                <div>
                                    <span>📝</span>
                                    Mid Exam
                                    <b>↓</b>
                                </div>

                                <div>
                                    <span>📄</span>
                                    Final Exam
                                    <b>↓</b>
                                </div>

                            </div>

                        </div>

                    </div>


                    {/* STEP 4 */}
                    <div className="step">

                        <div className="step-number">
                            04
                        </div>

                        <div className="step-content">

                            <h3>
                                View or download
                            </h3>

                            <p>
                                Open resources directly or download them
                                when you want to study offline.
                            </p>


                            <div className="view-download">

                                <div className="action-card">

                                    <div className="action-icon">
                                        ↗
                                    </div>

                                    <strong>
                                        View
                                    </strong>

                                    <span>
                                        PDFs open in your browser
                                    </span>

                                </div>


                                <div className="action-card">

                                    <div className="action-icon download">
                                        ↓
                                    </div>

                                    <strong>
                                        Download
                                    </strong>

                                    <span>
                                        Save resources to your device
                                    </span>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>


                <div className="how-bottom">

                    <span>✦</span>

                    <strong>
                        Need a quick overview?
                    </strong>

                    <p>
                        Use the AI Summary to understand what the course covers
                        before diving into the resources.
                    </p>

                </div>

            </section>
        </main>
    );
}

export default Home;
