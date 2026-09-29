// src/App.jsx
import { useEffect } from 'react';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import { VoxideWidget } from '@voxide/react';
import { ai } from './voxide/client.js';
import ThemeToggle from './components/ThemeToggle.jsx';
import CoursesPage from './pages/CoursesPage.jsx';
import CourseDetailPage from './pages/CourseDetailPage.jsx';

function App() {
  const navigate = useNavigate();

  useEffect(() => {
    ai.register({
      openCourse: {
        description: "Navigate to a specific course's detail page by its course ID.",
        params: {
          courseId: { type: "number", required: true }
        },
        handler: async ({ courseId }) => {
          navigate(`/courses/${courseId}`);
          return { status: "ok" };
        }
      },
      goHome: {
        description: "Navigate back to the courses list page.",
        params: {},
        handler: async () => {
          navigate('/');
          return { status: "ok" };
        }
      }
    });
  }, [navigate]);

  return (
    <div className="app-shell">
      <nav className="topbar">
        <Link to="/" className="brand">CourseVault</Link>
        <ThemeToggle />
      </nav>
      <Routes>
        <Route path="/" element={<CoursesPage />} />
        <Route path="/courses/:id" element={<CourseDetailPage />} />
      </Routes>
      <VoxideWidget client={ai} />
    </div>
  );
}

export default App;