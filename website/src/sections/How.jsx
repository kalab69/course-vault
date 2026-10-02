function How(){
    return (
         <section className="how-it-works" id="how">

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
    )
}
export default How