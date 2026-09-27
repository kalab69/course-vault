
import { Routes, Route, Link } from 'react-router-dom';
import CoursesPage from './pages/CoursesPage.jsx';
import CourseDetailPage from './pages/CourseDetailPage.jsx';

function App() {
  return (
    <div style={{ maxWidth: 900, margin: '0 auto', padding: '20px' }}>
      <nav style={{ marginBottom: 24 }}>
        <Link to="/">CourseVault Admin</Link>
      </nav>
      <Routes>
        <Route path="/" element={<CoursesPage />} />
        <Route path="/courses/:id" element={<CourseDetailPage />} />
      </Routes>
    </div>
  );
}

export default App;