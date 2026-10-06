import { useState, useEffect } from "react";
import { useSearchParams, useNavigate, Link } from "react-router-dom";
const API_BASE_URL = "https://course-vault-production-5ad8.up.railway.app";

function Search() {
  const [searchParams, setSearchParams] = useSearchParams();
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // Get current query param value 'q' from URL (?q=...)
  const query = searchParams.get("q") || "";

  // Fetch all courses on mount
  useEffect(() => {
    const fetchCourses = async () => {
      try {
        setLoading(true);
        const response = await fetch(`${API_BASE_URL}/api/courses`);

        if (!response.ok) {
          throw new Error("Failed to fetch courses");
        }

        const data = await response.json();
        setCourses(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, []);

  // Sync search input directly with URL search params
  const handleInputChange = (e) => {
    const val = e.target.value;
    if (val) {
      setSearchParams({ q: val }, { replace: true });
    } else {
      setSearchParams({}, { replace: true });
    }
  };

  const handleClear = () => {
    setSearchParams({}, { replace: true });
  };

  // Filter courses based on user input (searches code & courseName)
  const filteredCourses = courses.filter((course) => {
    if (!query.trim()) return true;
    const searchTerm = query.toLowerCase().trim();
    return (
      course.courseName.toLowerCase().includes(searchTerm) ||
      course.code.toLowerCase().includes(searchTerm)
    );
  });

  return (
    <div className="search-page">
      <div className="search-container">
        {/* Navigation & Header */}
        <div className="search-header">
          <Link to="/courses" className="back-link">
            ← Back to Courses
          </Link>
          <h1 className="search-title">Search Courses</h1>
          <p className="search-subtitle">
            Find courses instantly by typing their name or course code
          </p>
        </div>

        {/* Real-time Input Box */}
        <div className="search-bar-wrapper">
          <span className="search-bar-icon">⌕</span>
          <input
            type="text"
            className="search-input"
            placeholder="Type to search (e.g. CoSc2092, Data Structures)..."
            value={query}
            onChange={handleInputChange}
            autoFocus
          />
          {query && (
            <button
              className="clear-search-btn"
              onClick={handleClear}
              aria-label="Clear search"
            >
              ✕
            </button>
          )}
        </div>

        {/* Results / Status Area */}
        {error && <div className="error-message">Error: {error}</div>}

        {loading ? (
          <div className="loading-spinner">Loading courses...</div>
        ) : (
          <div className="search-results-section">
            <div className="results-count">
              {query.trim() ? (
                <>
                  Found <strong>{filteredCourses.length}</strong> result
                  {filteredCourses.length !== 1 ? "s" : ""} for "
                  <em>{query}</em>"
                </>
              ) : (
                <>
                  Showing all <strong>{courses.length}</strong> available courses
                </>
              )}
            </div>

            {filteredCourses.length === 0 ? (
              <div className="no-results-box">
                <p className="no-results-title">No matching courses found</p>
                <p className="no-results-desc">
                  Try checking for typos or searching with a different keyword.
                </p>
              </div>
            ) : (
              <div className="courses-grid">
                {filteredCourses.map((course) => (
                  <div
                    key={course.id}
                    className="search-course-card"
                    onClick={() => navigate(`/courses/${course.id}`)}
                  >
                    <div className="card-top">
                      <span className="course-code-pill">{course.code}</span>
                      <span className="course-year-badge">{course.yearLevel}</span>
                    </div>
                    <h3 className="course-card-title">{course.courseName}</h3>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default Search;