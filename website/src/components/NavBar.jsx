import { Link } from "react-router-dom";
import logo from "../assets/logo.png";

function NavBar() {
  return (
    <nav className="navbar">
      <Link to="/" className="navbar-logo">
        <img src={logo} alt="CourseVault" />
        <span>CourseVault</span>
      </Link>

      <div className="nav-links">
        <Link to="/" className="nav-link">Home</Link>
        <Link to="/#what" className="nav-link">What</Link>
        <Link to="/#how" className="nav-link">How</Link>
        <Link to="/#about" className="nav-link">About</Link>
        <Link to="/MyCourses" className="nav-link">My Courses</Link>
        <button>Download for desktop</button>
      </div>
    </nav>
  );
}

export default NavBar;