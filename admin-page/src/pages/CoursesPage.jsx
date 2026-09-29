// src/pages/CoursesPage.jsx
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api/client.js';
import { ai } from '../voxide/client.js';

const YEARS = ['FIRST', 'SECOND', 'THIRD', 'FOURTH'];

function CoursesPage() {
  const [courses, setCourses] = useState([]);
  const [yearFilter, setYearFilter] = useState('');
  const [form, setForm] = useState({ courseName: '', code: '', yearLevel: 'FIRST' });
  const [editingId, setEditingId] = useState(null);

  function loadCourses() {
    api.getCourses(yearFilter || undefined).then(setCourses);
  }

  useEffect(() => { loadCourses(); }, [yearFilter]);
  useEffect(() => {
  ai.register({
    createCourse: {
      description: "Create a new course with a name, code, and year level.",
      params: {
        courseName: { type: "string", required: true },
        code: { type: "string", required: true },
        yearLevel: { type: "string", required: true }
      },
      dangerous: true,
      handler: async ({ courseName, code, yearLevel }) => {
        await api.createCourse({ courseName, code, yearLevel });
        loadCourses();
        return { status: "ok" };
      }
    },
    filterCoursesByYear: {
      description: "Filter the visible course list by year level.",
      params: {
        yearLevel: { type: "string", required: true }
      },
      handler: async ({ yearLevel }) => {
        setYearFilter(yearLevel);
        return { status: "ok" };
      }
    }
  });
}, []);

  async function handleSubmit(e) {
    e.preventDefault();
    if (editingId) {
      await api.updateCourse(editingId, form);
    } else {
      await api.createCourse(form);
    }
    setForm({ courseName: '', code: '', yearLevel: 'FIRST' });
    setEditingId(null);
    loadCourses();
  }

  function startEdit(course) {
    setEditingId(course.id);
    setForm({ courseName: course.courseName, code: course.code, yearLevel: course.yearLevel });
  }

  async function handleDelete(id) {
    if (!confirm('Delete this course?')) return;
    await api.deleteCourse(id);
    loadCourses();
  }

  return (
    <div>
      <h1>Courses</h1>

      <form onSubmit={handleSubmit} style={{ marginBottom: 24, border: '1px solid #ccc', padding: 16, borderRadius: 8 }}>
        <h3>{editingId ? 'Edit Course' : 'Create Course'}</h3>
        <input
          placeholder="Course Name"
          value={form.courseName}
          onChange={e => setForm({ ...form, courseName: e.target.value })}
          required
        />
        <input
          placeholder="Code"
          value={form.code}
          onChange={e => setForm({ ...form, code: e.target.value })}
          required
        />
        <select value={form.yearLevel} onChange={e => setForm({ ...form, yearLevel: e.target.value })}>
          {YEARS.map(y => <option key={y} value={y}>{y}</option>)}
        </select>
        <button type="submit">{editingId ? 'Update' : 'Create'}</button>
        {editingId && (
          <button type="button" onClick={() => { setEditingId(null); setForm({ courseName: '', code: '', yearLevel: 'FIRST' }); }}>
            Cancel
          </button>
        )}
      </form>

      <label>Filter by year: </label>
      <select value={yearFilter} onChange={e => setYearFilter(e.target.value)}>
        <option value="">(all)</option>
        {YEARS.map(y => <option key={y} value={y}>{y}</option>)}
      </select>

      <ul className="ledger">
        {courses.map(c => (
          <li key={c.id} className="ledger-row">
            <div className="ledger-main">
              <Link to={`/courses/${c.id}`} className="ledger-title">{c.courseName}</Link>
              <span className="ledger-meta"><span className="code">{c.code}</span> · {c.yearLevel}</span>
            </div>
            <div className="ledger-actions">
              <button className="secondary" onClick={() => startEdit(c)}>Edit</button>
              <button className="danger" onClick={() => handleDelete(c.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default CoursesPage;