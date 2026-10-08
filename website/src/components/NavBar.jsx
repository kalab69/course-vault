import { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import logo from "../assets/logo.png";

function NavBar() {
  const location = useLocation();
  const [trackedSection, setTrackedSection] = useState("hero");
  const [menuOpen, setMenuOpen] = useState(false);

  const activeSection = location.pathname === "/" ? trackedSection : "";

  useEffect(() => {
    if (location.pathname !== "/") return;

    const sectionIds = ["hero", "what", "how", "about"];

    const observerOptions = {
      root: null,
      rootMargin: "-10% 0px -40% 0px",
      threshold: 0,
    };

    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          setTrackedSection(entry.target.id);
        }
      });
    }, observerOptions);

    sectionIds.forEach((id) => {
      const element = document.getElementById(id);
      if (element) observer.observe(element);
    });

    const handleScroll = () => {
      if (window.scrollY < 100) {
        setTrackedSection("hero");
      }
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      observer.disconnect();
      window.removeEventListener("scroll", handleScroll);
    };
  }, [location.pathname]);

  const closeMenu = () => setMenuOpen(false);

  return (
    <nav className="navbar">
      <Link to="/" className="navbar-logo" onClick={closeMenu}>
        <img src={logo} alt="CourseVault" />
        <span>CourseVault</span>
      </Link>

      {/* collaps nav bar*/}
      <button
        className={`hamburger ${menuOpen ? "open" : ""}`}
        aria-label="Toggle menu"
        aria-expanded={menuOpen}
        onClick={() => setMenuOpen((o) => !o)}
      >
        <span></span>
        <span></span>
        <span></span>
      </button>

      {/* dropdown class toggles on mobile */}
      <div className={`nav-links ${menuOpen ? "open" : ""}`}>
        <Link
          to="/#hero"
          className={`nav-link ${activeSection === "hero" ? "active" : ""}`}
          onClick={closeMenu}
        >
          Home
        </Link>
        <Link
          to="/#what"
          className={`nav-link ${activeSection === "what" ? "active" : ""}`}
          onClick={closeMenu}
        >
          What
        </Link>
        <Link
          to="/#how"
          className={`nav-link ${activeSection === "how" ? "active" : ""}`}
          onClick={closeMenu}
        >
          How
        </Link>
        <Link
          to="/#about"
          className={`nav-link ${activeSection === "about" ? "active" : ""}`}
          onClick={closeMenu}
        >
          About
        </Link>
        <Link
          to="/Courses"
          className={`nav-link ${
            location.pathname.startsWith("/MyCourses") ? "active" : ""
          }`}
          onClick={closeMenu}
        >
          Courses
        </Link>

        <a
          href="https://github.com/kalab69/course-vault/releases/download/v1.0.1/CourseVault-1.0.1.exe"
          download
        >
          <button>Download for desktop</button>
        </a>
      </div>
    </nav>
  );
}

export default NavBar;