import logo from "../assets/logo.png";
function Footer() {
    return (
        <footer className="site-footer" id="about">

            <div className="footer-content">

                {/* Brand */}
                <div className="footer-brand">

                    <img
                       src={logo} alt="CourseVault"
                        alt="CourseVault logo"
                        className="footer-logo"
                    />

                    <p>
                        CourseVault is a shared study resource platform for
                        university students. Find organized notes, past exams,
                        tutorials, and AI-powered course summaries in one place.
                    </p>

                    <a
                        href="https://github.com/kalab69/course-vault"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="footer-repo"
                    >
                        <svg
                            viewBox="0 0 24 24"
                            className="github-icon"
                            aria-hidden="true"
                        >
                            <path
                                fill="currentColor"
                                d="M12 .5C5.65.5.5 5.65.5 12c0 5.08
                        3.29 9.39 7.86 10.91.58.11.79-.25.79-.56
                        0-.28-.01-1.02-.02-2-3.2.7-3.88-1.54
                        -3.88-1.54-.53-1.33-1.28-1.69-1.28-1.69
                        -1.05-.72.08-.7.08-.7 1.16.08 1.77
                        1.19 1.77 1.19 1.03 1.77 2.7 1.26
                        3.36.96.1-.75.4-1.26.73-1.55-2.55-.29
                        -5.23-1.28-5.23-5.69 0-1.26.45-2.29
                        1.19-3.1-.12-.29-.52-1.47.11-3.06
                        0 0 .97-.31 3.18 1.18a11.06 11.06 0 0
                        1 5.8 0c2.21-1.49 3.18-1.18 3.18-1.18
                        .63 1.59.23 2.77.11 3.06.74.81 1.19
                        1.84 1.19 3.1 0 4.42-2.69 5.4-5.25
                        5.68.41.35.78 1.04.78 2.1 0 1.52-.01
                        2.75-.01 3.12 0 .31.21.68.8.56A11.51
                        11.51 0 0 0 23.5 12C23.5 5.65 18.35.5
                        12 .5Z"
                            />
                        </svg>

                        <span>CourseVault</span>

                        <span className="repo-arrow">↗</span>
                    </a>

                </div>


                {/* CourseVault */}
                <div className="footer-column">

                    <h3>CourseVault</h3>

                    <p className="footer-column-description">
                        Everything you need to find and organize your
                        university study resources.
                    </p>

                    <a href="#how" className="footer-link">
                        How it works
                    </a>

                    <a href="#about" className="footer-link">
                        About CourseVault
                    </a>

                </div>


                {/* Connect */}
                <div className="footer-column">

                    <h3>Connect</h3>

                    <a
                        href="https://github.com/abeltheone21"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="developer-link"
                    >
                        <svg viewBox="0 0 24 24" className="github-icon">
                            <path
                                fill="currentColor"
                                d="M12 .5C5.65.5.5 5.65.5 12c0 5.08
                        3.29 9.39 7.86 10.91.58.11.79-.25.79-.56
                        0-.28-.01-1.02-.02-2-3.2.7-3.88-1.54
                        -3.88-1.54-.53-1.33-1.28-1.69-1.28-1.69
                        -1.05-.72.08-.7.08-.7 1.16.08 1.77
                        1.19 1.77 1.19 1.03 1.77 2.7 1.26
                        3.36.96.1-.75.4-1.26.73-1.55-2.55-.29
                        -5.23-1.28-5.23-5.69 0-1.26.45-2.29
                        1.19-3.1-.12-.29-.52-1.47.11-3.06
                        0 0 .97-.31 3.18 1.18a11.06 11.06 0 0
                        1 5.8 0c2.21-1.49 3.18-1.18 3.18-1.18
                        .63 1.59.23 2.77.11 3.06.74.81 1.19
                        1.84 1.19 3.1 0 4.42-2.69 5.4-5.25
                        5.68.41.35.78 1.04.78 2.1 0 1.52-.01
                        2.75-.01 3.12 0 .31.21.68.8.56A11.51
                        11.51 0 0 0 23.5 12C23.5 5.65 18.35.5
                        12 .5Z"
                            />
                        </svg>
                        Abel
                    </a>

                    <a
                        href="https://github.com/ab2160"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="developer-link"
                    >
                        <svg viewBox="0 0 24 24" className="github-icon">
                            <path
                                fill="currentColor"
                                d="M12 .5C5.65.5.5 5.65.5 12c0 5.08
                        3.29 9.39 7.86 10.91.58.11.79-.25.79-.56
                        0-.28-.01-1.02-.02-2-3.2.7-3.88-1.54
                        -3.88-1.54-.53-1.33-1.28-1.69-1.28-1.69
                        -1.05-.72.08-.7.08-.7 1.16.08 1.77
                        1.19 1.77 1.19 1.03 1.77 2.7 1.26
                        3.36.96.1-.75.4-1.26.73-1.55-2.55-.29
                        -5.23-1.28-5.23-5.69 0-1.26.45-2.29
                        1.19-3.1-.12-.29-.52-1.47.11-3.06
                        0 0 .97-.31 3.18 1.18a11.06 11.06 0 0
                        1 5.8 0c2.21-1.49 3.18-1.18 3.18-1.18
                        .63 1.59.23 2.77.11 3.06.74.81 1.19
                        1.84 1.19 3.1 0 4.42-2.69 5.4-5.25
                        5.68.41.35.78 1.04.78 2.1 0 1.52-.01
                        2.75-.01 3.12 0 .31.21.68.8.56A11.51
                        11.51 0 0 0 23.5 12C23.5 5.65 18.35.5
                        12 .5Z"
                            />
                        </svg>
                        Abreham
                    </a>

                    <a
                        href="https://github.com/kalab69"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="developer-link"
                    >
                        <svg viewBox="0 0 24 24" className="github-icon">
                            <path
                                fill="currentColor"
                                d="M12 .5C5.65.5.5 5.65.5 12c0 5.08
                        3.29 9.39 7.86 10.91.58.11.79-.25.79-.56
                        0-.28-.01-1.02-.02-2-3.2.7-3.88-1.54
                        -3.88-1.54-.53-1.33-1.28-1.69-1.28-1.69
                        -1.05-.72.08-.7.08-.7 1.16.08 1.77
                        1.19 1.77 1.19 1.03 1.77 2.7 1.26
                        3.36.96.1-.75.4-1.26.73-1.55-2.55-.29
                        -5.23-1.28-5.23-5.69 0-1.26.45-2.29
                        1.19-3.1-.12-.29-.52-1.47.11-3.06
                        0 0 .97-.31 3.18 1.18a11.06 11.06 0 0
                        1 5.8 0c2.21-1.49 3.18-1.18 3.18-1.18
                        .63 1.59.23 2.77.11 3.06.74.81 1.19
                        1.84 1.19 3.1 0 4.42-2.69 5.4-5.25
                        5.68.41.35.78 1.04.78 2.1 0 1.52-.01
                        2.75-.01 3.12 0 .31.21.68.8.56A11.51
                        11.51 0 0 0 23.5 12C23.5 5.65 18.35.5
                        12 .5Z"
                            />
                        </svg>
                        Kalab
                    </a>

                </div>

            </div>


            {/* Feature strip */}
            <div className="footer-features">

                <div className="footer-feature">
                    <span className="feature-icon">⌕</span>
                    <div>
                        <strong>Find resources</strong>
                        <p>Search by course or year level</p>
                    </div>
                </div>

                <div className="footer-feature">
                    <span className="feature-icon">▣</span>
                    <div>
                        <strong>Stay organized</strong>
                        <p>Notes and exams in one place</p>
                    </div>
                </div>

                <div className="footer-feature">
                    <span className="feature-icon">✦</span>
                    <div>
                        <strong>Study smarter</strong>
                        <p>Use AI summaries when you need them</p>
                    </div>
                </div>

            </div>


            <div className="footer-bottom">
                <span>© 2026 CourseVault</span>
                <span>Built for university students</span>
            </div>

        </footer>


    );
}

export default Footer;
