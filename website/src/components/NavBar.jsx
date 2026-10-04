import { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import logo from "../assets/logo.png";

function NavBar() {
  const location = useLocation();
  const [activeSection, setActiveSection] = useState("hero");

  useEffect(() => {
    // If not on Home page, remove section highlights
    if (location.pathname !== "/") {
      setActiveSection("");
      return;
    }

    const sectionIds = ["hero", "what", "how", "about"];

    const observerOptions = {
      root: null,
      // Adjust margin so hero triggers as soon as you're near top
      rootMargin: "-10% 0px -40% 0px",
      threshold: 0,
    };

    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          setActiveSection(entry.target.id);
        }
      });
    }, observerOptions);

    sectionIds.forEach((id) => {
      const element = document.getElementById(id);
      if (element) observer.observe(element);
    });

    // Also reset to 'hero' when scrolled to top
    const handleScroll = () => {
      if (window.scrollY < 100) {
        setActiveSection("hero");
      }
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      observer.disconnect();
      window.removeEventListener("scroll", handleScroll);
    };
  }, [location.pathname]);

  return (
    <nav className="navbar">
      <Link to="/" className="navbar-logo">
        <img src={logo} alt="CourseVault" />
        <span>CourseVault</span>
      </Link>

      <div className="nav-links">
        {/* Point hero ID directly to Home link */}
        <Link
          to="/#hero"
          className={`nav-link ${activeSection === "hero" ? "active" : ""}`}
        >
          Home
        </Link>

        <Link
          to="/#what"
          className={`nav-link ${activeSection === "what" ? "active" : ""}`}
        >
          What
        </Link>

        <Link
          to="/#how"
          className={`nav-link ${activeSection === "how" ? "active" : ""}`}
        >
          How
        </Link>

        <Link
          to="/#about"
          className={`nav-link ${activeSection === "about" ? "active" : ""}`}
        >
          About
        </Link>

        <Link
          to="/Courses"
          className={`nav-link ${
            location.pathname.startsWith("/MyCourses") ? "active" : ""
          }`}
        >
          Courses
        </Link>

        <button>Download for desktop</button>
      </div>
    </nav>
  );
}

export default NavBar;