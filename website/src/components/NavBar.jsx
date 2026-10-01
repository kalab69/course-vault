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
                <Link to="/how-to-use" className="nav-link">How to Use</Link>
                <Link to="/#about" className="nav-link">
                    About
                </Link>
                <Link to="/courses" className="nav-link">Courses</Link>
                <Link to="/search" className="nav-link">Search</Link>
                <button>Download for desktop</button>
            </div>

        </nav>
    );
}

export default NavBar;