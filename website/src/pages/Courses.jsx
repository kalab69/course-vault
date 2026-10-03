import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

const API_BASE_URL = "http://localhost:8080";

const YEARS = [
  { label: "Year I", value: "FIRST" },
  { label: "Year II", value: "SECOND" },
  { label: "Year III", value: "THIRD" },
  { label: "Year IV", value: "FOURTH" },
];

function Courses() {
  const [coursesByYear, setCoursesByYear] = useState({
    FIRST: [],
    SECOND: [],
    THIRD: [],
    FOURTH: [],
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [openYear, setOpenYear] = useState("Year I");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAllYears = async () => {
      try {
        setLoading(true);
        setError(null);

        // Concurrently call GET /api/courses?year={year} for all 4 years
        const responses = await Promise.all(
          YEARS.map(async (year) => {
            const res = await fetch(
              `${API_BASE_URL}/api/courses?year=${year.value}`
            );
            if (!res.ok) {
              throw new Error(`Failed to fetch courses for ${year.label}`);
            }
            return res.json();
          })
        );

        setCoursesByYear({
          FIRST: responses[0],
          SECOND: responses[1],
          THIRD: responses[2],
          FOURTH: responses[3],
        });
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchAllYears();
  }, []);

  const toggleYear = (yearLabel) => {
    setOpenYear(openYear === yearLabel ? null : yearLabel);
  };

  // Calculate total enrolled courses across all endpoints
  const totalCourses = Object.values(coursesByYear).reduce(
    (acc, list) => acc + list.length,
    0
  );

  return (
    <div className="courses-page">
      {/* Top Banner & Search Button */}
      <div className="courses-header">
        <div>
          <h1 className="welcome-title">Welcome</h1>
          <p className="welcome-subtitle">
            {loading
              ? "Loading enrolled courses..."
              : `${totalCourses} courses you have enrolled in`}
          </p>
        </div>

        <Link to="/search" className="search-course-button">
          <span className="search-icon">⌕</span>
          Search Course
        </Link>
      </div>

      {/* Stat Cards */}
      <div className="stats-container">
        <div className="stat-card">
          <div className="stat-icon">📚</div>
          <div className="stat-number">{loading ? "..." : totalCourses}</div>
          <div className="stat-label">Enrolled</div>
        </div>

        <div className="stat-card">
          <div className="stat-icon">☑</div>
          <div className="stat-number">8</div>
          <div className="stat-label">Resources Downloaded</div>
        </div>
      </div>

      {/* Department Section */}
      <div className="department-container">
        <h2 className="department-title">Computer Science</h2>

        {error && (
          <div className="error-message">Error loading courses: {error}</div>
        )}

        {loading ? (
          <div className="loading-spinner">Loading courses...</div>
        ) : (
          <div className="years-list">
            {YEARS.map(({ label, value }) => {
              const isOpen = openYear === label;
              const yearCourses = coursesByYear[value] || [];

              return (
                <div key={label} className="year-group">
                  <button
                    className={`year-toggle ${isOpen ? "active" : ""}`}
                    onClick={() => toggleYear(label)}
                  >
                    <span className="toggle-arrow">{isOpen ? "▼" : "▶"}</span>
                    <span className="year-label">{label}</span>
                    <span className="year-count">({yearCourses.length})</span>
                  </button>

                  {isOpen && (
                    <div className="courses-accordion-content">
                      {yearCourses.length === 0 ? (
                        <p className="no-courses">
                          No courses found for {label}.
                        </p>
                      ) : (
                        yearCourses.map((course) => (
                          <div
                            key={course.id}
                            className="course-item-card"
                            onClick={() => navigate(`/courses/${course.id}`)}
                          >
                            <span className="course-code-pill">
                              {course.code}
                            </span>
                            <h3 className="course-item-title">
                              {course.courseName}
                            </h3>
                          </div>
                        ))
                      )}
                    </div>
                  )}
                </div>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
}

export default Courses;