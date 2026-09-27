// src/pages/CourseDetailPage.jsx
import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { api } from '../api/client.js';

const TYPES = ['NOTES', 'MIDTERM', 'FINAL'];

function CourseDetailPage() {
  const { id } = useParams();
  const [course, setCourse] = useState(null);
  const [resources, setResources] = useState([]);
  const [links, setLinks] = useState([]);

  const [resourceForm, setResourceForm] = useState({ title: '', type: 'NOTES', file: null });
  const [linkForm, setLinkForm] = useState({ topic: '', title: '', url: '' });

  function loadAll() {
    api.getCourse(id).then(setCourse);
    api.getResources(id).then(setResources);
    api.getLinks(id).then(setLinks);
  }

  useEffect(() => { loadAll(); }, [id]);

  async function handleUpload(e) {
    e.preventDefault();
    if (!resourceForm.file) return alert('Pick a file first');

    const formData = new FormData();
    formData.append('file', resourceForm.file);
    formData.append('title', resourceForm.title);
    formData.append('courseId', id);
    formData.append('type', resourceForm.type);

    await api.uploadResource(formData);
    setResourceForm({ title: '', type: 'NOTES', file: null });
    loadAll();
  }

  async function handleDeleteResource(resId) {
    if (!confirm('Delete this resource?')) return;
    await api.deleteResource(resId);
    loadAll();
  }

  async function handleAddLink(e) {
    e.preventDefault();
    await api.addLink(id, linkForm);
    setLinkForm({ topic: '', title: '', url: '' });
    loadAll();
  }

  async function handleDeleteLink(linkId) {
    if (!confirm('Delete this link?')) return;
    await api.deleteLink(linkId);
    loadAll();
  }

  if (!course) return <p>Loading...</p>;

  return (
    <div>
      <Link to="/">&larr; Back to courses</Link>
      <h1>{course.courseName} ({course.code})</h1>
      <p>Year: {course.yearLevel}</p>

      <h2>Resources</h2>
      <form onSubmit={handleUpload} style={{ marginBottom: 16, border: '1px solid #ccc', padding: 16, borderRadius: 8 }}>
        <input
          placeholder="Title"
          value={resourceForm.title}
          onChange={e => setResourceForm({ ...resourceForm, title: e.target.value })}
          required
        />
        <select
          value={resourceForm.type}
          onChange={e => setResourceForm({ ...resourceForm, type: e.target.value })}
        >
          {TYPES.map(t => <option key={t} value={t}>{t}</option>)}
        </select>
        <input
          type="file"
          accept=".pdf,image/jpeg,image/png"
          onChange={e => setResourceForm({ ...resourceForm, file: e.target.files[0] })}
        />
        <button type="submit">Upload</button>
      </form>

      <ul>
        {resources.map(r => (
          <li key={r.id}>
            [{r.type}] {r.title}
            {' '}<a href={`http://localhost:8080${r.downloadUrl}`} target="_blank" rel="noreferrer">Download</a>
            <button onClick={() => handleDeleteResource(r.id)} style={{ marginLeft: 6 }}>Delete</button>
          </li>
        ))}
      </ul>

      <h2>External Links</h2>
      <form onSubmit={handleAddLink} style={{ marginBottom: 16, border: '1px solid #ccc', padding: 16, borderRadius: 8 }}>
        <input
          placeholder="Topic"
          value={linkForm.topic}
          onChange={e => setLinkForm({ ...linkForm, topic: e.target.value })}
        />
        <input
          placeholder="Title"
          value={linkForm.title}
          onChange={e => setLinkForm({ ...linkForm, title: e.target.value })}
          required
        />
        <input
          placeholder="URL"
          value={linkForm.url}
          onChange={e => setLinkForm({ ...linkForm, url: e.target.value })}
          required
        />
        <button type="submit">Add Link</button>
      </form>

      <ul>
        {links.map(l => (
          <li key={l.id}>
            <a href={l.url} target="_blank" rel="noreferrer">{l.title}</a> ({l.topic})
            <button onClick={() => handleDeleteLink(l.id)} style={{ marginLeft: 6 }}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default CourseDetailPage;